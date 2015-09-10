from sqlalchemy import create_engine, MetaData, Table
from sqlalchemy.orm import mapper, sessionmaker
import sys
class Device(object):
	pass
class Port(object):
	pass
class Line(object):
	pass
	
def loadSession():
    engine = create_engine("mysql+mysqldb://msns:#WashUBears#@10.39.194.183/NetworkCMDB_Test")
    metadata = MetaData(engine)
    dev = Table('device', metadata, autoload=True)
    devA = Table('ports', metadata, autoload=True)
    devB = Table('linesid', metadata, autoload=True)
    mapper(Device, dev)
    mapper(Port, devA)
    mapper(Line, devB)
    Session = sessionmaker(bind=engine)
    session = Session()
    return session
    
if __name__ == "__main__":
    session = loadSession()
    res = session.query(Device).all()
    resA = session.query(Port).all()
    resB = session.query(Line).all()
    

try:
	key = sys.argv[1]
	print "Device Name: " , key
except IndexError:
	print 'Please Provide a Device Name'
	exit()
for x in res:
	if key == x.u_name:
		key = x.sys_id
for y in resA:
	if key == y.u_device_id:
		print 'Port: ' , y.PortId
		print '-------------------------------------------------------------------------------------'
		for z in resB:
			if y.sys_id == z.u_port:
				print "--", z.LineId
			
