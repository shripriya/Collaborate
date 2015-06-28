Ext.define('CollaborateApp.store.FileGridStore', {
	extend : 'Ext.data.TreeStore',
	itemId : 'fileGridStore',
	model : 'CollaborateApp.model.FileModel',
	proxy : {
		type : 'ajax',
		url : URL.requestURL + 'getFiles',
		reader : {
			type : 'json',
			messageProperty : 'statusCode'
		}
	},
	folderSort : true,
	autoLoad : true
});