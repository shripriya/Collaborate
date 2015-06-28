/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var URL = new Object();

URL.baseURL = 'collaborateserver/request/';
URL.requestURL = URL.baseURL + 'process/';
URL.serviceURL = URL.baseURL + 'process/ccontroller/';
URL.loginURL = URL.baseURL + 'process/login';

var CollaborateUtil = new Object();

CollaborateUtil.callService = function(serviceName, type, data, callback) {

	$.ajax({
		type : CollaborateUtil.validateRequestType(type),
		url : URL.serviceURL + serviceName,
		data : CollaborateUtil.formatDataForRequestType(type, data),
		success : callback,
		dataType : 'json'
	});
};

CollaborateUtil.validateRequestType = function(type) {

	if (type !== 'POST' && type !== 'GET') {
		return 'POST';
	}
	return type;
};

CollaborateUtil.formatDataForRequestType = function(type, data) {

	if (type === 'POST') {
		return JSON.stringify(data);
	} else {
		return data;
	}
};

CollaborateUtil.doLogout = function(data, callback) {

	CollaborateUtil.callPOSTService('logout', data, callback);
};

CollaborateUtil.callPOSTService = function(request, data, callback) {

	$.ajax({
		type : 'POST',
		url : URL.requestURL + request,
		data : JSON.stringify(data),
		success : callback,
		dataType : 'json'
	});
};
