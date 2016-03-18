import os
import sys
import datetime
import json
from sqlalchemy import Column, ForeignKey, Integer, String, Numeric, DateTime, Table
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import relationship, relation
from sqlalchemy import create_engine

Base = declarative_base()

association_table = Table('association', Base.metadata,
    Column('user_id', Integer, ForeignKey('user.id')),
    Column('buddy_id', Integer, ForeignKey('user.id'))
)

class User(Base):
    __tablename__ = 'user'
    # Here we define columns for the table person
    # Notice that each column is also a normal Python instance attribute.
    id = Column(Integer, primary_key=True)
    name = Column(String(75), nullable=False)
    # password = Column(String(75), nullable=False)
    email = Column(String(75), nullable=False, unique=True)
    days = relationship("Day", backref="user")
    cigs_per_day = Column(Integer, nullable=False)
    time = Column(String(75), nullable=True)
    money_saved = Column(Numeric(10,2), nullable=True, default=0)
    life_regained = Column(Integer, nullable=True, default=0)
    days_free = Column(Integer, nullable=True, default=0)

    price_per_pack = Column(Numeric(4,2), nullable=False)
    cravings_resisted = Column(Integer, default=0)
    current_streak = Column(Integer, default=1)
    longest_streak = Column(Integer, default=1)
    cigs_not_smoked = Column(Integer, default=0)
    buddy_id = Column(Integer, ForeignKey('user.id'))
    buddy = relation(
                    'User',secondary=association_table,
                    primaryjoin=association_table.c.user_id==id,
                    secondaryjoin=association_table.c.buddy_id==id,
                    backref="buddies")


    def toJSON(self):
        dayList = []
        for day in self.days:
            dayList.append(day.toJSON())
        m = []
        for buddy in self.buddies:
            m.append(buddy.id)
        returnData = {"id" : self.id, "name":self.name, "email": self.email, "days":json.dumps(dayList), "cigs_per_day": self.cigs_per_day, "money_saved": float(self.money_saved),"time":self.time,"life_regained":self.life_regained,"days_free":self.days_free}
        return returnData

    def getBuddies(self):
        m = []
        for buddy in self.buddies:
            m.append(buddy.id)
        return m
class Day(Base):
    __tablename__ = 'day'

    id = Column(Integer, primary_key=True)
    date = Column(DateTime, nullable=False)
    user_id = Column(Integer, ForeignKey('user.id'), nullable=False)
    cigs_smoked = Column(Integer, default=0)
    cravings = Column(Integer, default=0)
    cravings_resisted = Column(Integer, default=0)

    def toJSON(self):
        returnData = {"id":self.id,"date":str(self.date),"user_id":self.user_id, "cigs_not_smoked":self.user.cigs_per_day - self.cigs_smoked}
        return returnData

class Feed(Base):
    __tablename__ = 'feed'

    id = Column(Integer, primary_key=True)
    posted_by = Column(Integer, ForeignKey('user.id'), nullable=False)
    description = Column(String(255), nullable=False)
    date = Column(DateTime, nullable=False)
    like = Column(Integer, default=0)
    dislike = Column(Integer, default=0)
    def toJSON(self):
        returnData = {"feedid":self.id, "description":self.description, "date":str(self.date), "likes":self.like, "dislikes":self.dislike}
        return returnData
# Create an engine that stores data in the local directory's
# sqlalchemy_example.db file.
engine = create_engine('mysql+pymysql://root:stopsmokingtoday@localhost/apidb?charset=utf8&use_unicode=0', pool_recycle=3600)

# Create all tables in the engine. This is equivalent to "Create Table"
# statements in raw SQL.
Base.metadata.create_all(engine)
