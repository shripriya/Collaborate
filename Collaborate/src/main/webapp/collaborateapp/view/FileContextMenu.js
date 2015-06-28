Ext.define('CollaborateApp.view.FileContextMenu', {
	extend : 'Ext.menu.Menu',
	xtype : 'filecontextmenu',
	itemId : 'fileContextMenu',
	width : 100,
	record : '',
	initComponent : function() {

		var me = this;

		Ext.applyIf(me, {
			items : [ {
				text : 'New Folder',
				iconCls : 'folderIcon',
				itemId : 'ctxMenuNewFolder'
			}, {
				text : 'Upload File',
				iconCls : 'uploadIcon',
				itemId : 'ctxMenuUpload'
			}, {
				text : 'Delete',
				iconCls : 'deleteIcon',
				itemId : 'ctxMenuDelete'
			} ]
		});

		me.callParent(arguments);
	},

	setRecord : function(record) {

		this.record = record;
	},

	getRecord : function() {

		return this.record;
	}

});
