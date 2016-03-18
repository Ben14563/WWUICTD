
import bottle
from bottle import HTTPError, run, route, template, request, response, static_file, get, install
from sql_ex import Base, User, Day, Feed
from bottle.ext import sqlalchemy
from sqlalchemy import create_engine
from datetime import datetime


import json

engine = create_engine('mysql+pymysql://root:stopsmokingtoday@localhost/apidb?charset=utf8&use_unicode=0', pool_recycle=3600)
Base.metadata.bind = engine

plugin = sqlalchemy.Plugin(
    engine, # SQLAlchemy engine created with create_engine function.
    keyword='db', # Keyword used to inject session database in a route (default 'db').
    create=False, # If it is true, execute `metadata.create_all(engine)` when plugin is applied (default False).
    commit=True, # If it is true, plugin commit changes after route is executed (default True).
    use_kwargs=False # If it is true and keyword is not defined, plugin uses **kwargs argument to inject session database (default False).
)
install(plugin)

# Given a user id return all information about them
@get('/user/:id')
def getUser(db, id):
    try:
        user = db.query(User).filter(User.id == id).one()
        if user:
            return user.toJSON()
    except:
        return json.dumps("Not a valid user id")

#create a new user and return its id
@get('/user/add')
def addUser(db):
    newUser = User(name=request.GET["name"], email=request.GET["email"], cigs_per_day=request.GET["cigs_per_day"], price_per_pack=request.GET["price_per_pack"])
    db.add(newUser)
    db.commit()
    addBuddy(db,newUser.id,newUser.email)
    return json.dumps(newUser.id)


# Given a user id and a date, return all the information from that day.
@get('/user/:id/update/:field')
def getUserDay(db, id, field):
    user = db.query(User).filter(User.id == id).one()
    # date = db.query(Day).filter(Day.user_id == user.id).filter(datetime.strptime(day, '%m-%d-%Y')==Day.date).one()
    if (field == "cigs_smoked"):
        description = " has smoked a cigarette. Encourage them to not keep smoking!"
    elif (field == "cravings"):
        description = " is craving a cigarette. Encourage them to not give in!"
    elif (field == "cravings_resisted"):
        description = " resisted a craving! Congratulate them!"
    GETString = user.name + description

    feedGET = Feed(posted_by=user.id,description=GETString,date=datetime.now())
    db.add(feedGET)
    db.commit()
    return json.dumps(field)

# Given a user id, return the feed information for that user
@get('/user/:id/feed')
def getUserFeed(db, id):
    user = db.query(User).filter(User.id == id).one()
    buddies = user.getBuddies()
    feedData = db.query(Feed).filter(Feed.posted_by.in_(buddies)).order_by(Feed.date.desc()).all()
    returnData = []
    for d in feedData:
        returnData.append(d.toJSON())
    return json.dumps(returnData)

# Given a user id and a date, return all the information from that day.
@get('/user/:id/:day')
def getUserDay(db, id, day):
    user = db.query(User).filter(User.id == id).one()
    date = db.query(Day).filter(Day.user_id == user.id).filter(datetime.strptime(day, '%m-%d-%Y')==Day.date).one()
    return date.toJSON()

@get('/feed/:id/like')
def likePost(db,id):
    post = db.query(Feed).filter(Feed.id == id).one()
    post.like += 1
    db.commit()
    return json.dumps(True)

@get('/feed/:id/dislike')
def likePost(db,id):
    post = db.query(Feed).filter(Feed.id == id).one()
    post.dislike += 1
    db.commit()
    return json.dumps(True)

@get('/day/add/:id')
def addUserDay(db,id):
    user = db.query(User).filter(User.id == id).one()
    try:
        date = db.query(Day).filter(Day.user_id == user.id).filter(datetime.today().replace(hour=0, minute=0, second=0, microsecond=0)==Day.date).one()
        return json.dumps("Date already in table for user")
    except:
        date = datetime.today().replace(hour=0, minute=0, second=0, microsecond=0)
        newDay = Day(date=date, user_id=user.id)
        db.add(newDay)
        user.days.append(newDay)
        db.commit()
        return json.dumps(True)

@get('/user/:id/buddy/add/:email')
def addBuddy(db,id,email):
    try:
        user = db.query(User).filter(User.id == id).one()
        newBuddy = db.query(User).filter(User.email == email).one()
        newBuddy.buddy.append(user)
        db.commit()
        return json.dumps(True)
    except:
        return json.dumps(False)

@get('/buddies/:id')
def getBuddies(db,id):
    user = db.query(User).filter(User.id == id).one()
    buddies = user.getBuddies()
    returnData = []
    for d in buddies:
        buddy = db.query(User).filter(User.id == d).one()
        returnData.append(buddy.name)
    return json.dumps(returnData)


# run(
#         host     = '0.0.0.0',
#         port     = 8080,
#         reloader = True,        # restarts the server every time edit a module file
#         debug    = True         # Comment out it before deploy
#         )
