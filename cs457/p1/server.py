import socket
import sys
from thread import *
def clientthread(conn):
    conn.send('Welcome traveler\n')
    while True:
        data = conn.recv(1024)
        if data == 'leave\n':
            break
        reply = 'ECHO: ' + data
        conn.sendall(reply)
    conn.close()
HOST = ''
PORT = 8888
s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
print 'Socket created'
try:
    s.bind((HOST,PORT))
except socket.error,msg:
    print 'Bind failed. Error code: ' + str(msg[0]) + ' Message: ' + msg[1]
    sys.exit()
print 'Socket bind complete'
s.listen(10)
print 'Socket now listening'
while True:
    conn,addr = s.accept()
    print 'Connected with ' + addr[0] + ':' + str(addr[1])
    start_new_thread(clientthread, (conn,))
conn.close()
s.close()
