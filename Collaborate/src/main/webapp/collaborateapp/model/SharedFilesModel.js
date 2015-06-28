Ext.define('CollaborateApp.model.SharedFilesModel', {
	extend : 'Ext.data.Model',
	xtype : 'fileModel',
	fields : [ {
		name : 'fileName',
		type : 'string'
	}, {
		name : 'filePath',
		type : 'string'
	}, {
		name : 'sharedBy',
		type : 'string'
	}, {
		name : 'sharedWith',
		type : 'string'
	}, {
		name : 'ownerLName',
		type : 'string'
	}, {
		name : 'ownerFName',
		type : 'string'
	} ]
});