Ext.define('CollaborateApp.model.FileModel', {
	extend : 'Ext.data.TreeModel',
	xtype : 'fileModel',
	fields : [ {
		name : 'name',
		type : 'string'
	}, {
		name : 'size',
		type : 'string'
	}, {
		name : 'path',
		type : 'string'
	} ]
});