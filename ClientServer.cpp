#include "ClientServer.h"
bool flag = true;

bool ClientServer::connectServer(const char* ip,int port) {
	odSocket.Init();
	odSocket.Create(AF_INET , SOCK_STREAM,0);
	bool result = odSocket.Connect(ip, port);
	if (result) {
		log("connect success");

		std::thread recvThread = std::thread(ClientServer::recvData);
		recvThread.detach();
		return true;
	}
	else {
		log("connect fail");
		return false;
	}

}

void ClientServer::sendDataByString(std::string data) {
	bool result =  odSocket.Send(data.c_str(), data.size(), 0);
	if (result) {
		log("Send: %s", data.c_str());
	}
	else {
		log("Send fail");
	}
}
void ClientServer::sendDataByVector(cocos2d::ValueVector list) {
	auto encodeJson = EncodeJson::createWithArray(list);
	std::string data = encodeJson->encode();
	bool result = odSocket.Send(data.c_str(), data.size(), 0);
	if (result) {
		log("Send: %s", data.c_str());
	}
	else {
		log("Send fail");
	}
}

void ClientServer::recvData() {
	while (flag) {
		odSocket.Recv(buffer, 512, 0);
		log("receive: %s", buffer);
	}
}

void ClientServer::closeClient() {
	flag = false;
	std::string closeMsg = "ConnectionClose";
	odSocket.Send(closeMsg.c_str(), closeMsg.size());
	odSocket.Close();
}
