#ifndef _CLIENTSERVER_H_
#define _CLIENTSERVER_H_
#include "ODSocket\ODSocket.h"
#include "cocos2d.h"
#include "EncodeJson.h"
USING_NS_CC;
#pragma once
class ClientServer
{
private:
	static ODSocket odSocket;
	static bool flag;
	static char buffer[512];
public:

	static bool connectServer(const char* ip ,int port );
	
	static void sendDataByString(std::string data);
    static void sendDataByVector(cocos2d::ValueVector list);

	static void recvData();

	static void closeClient();
};


#endif // !_CLIENTSERVER_H_
