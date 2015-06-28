Ext.define('CollaborateApp.view.ShareFile', {
	extend : 'Ext.window.Window',
	xtype : 'sharefilewindow',
	title : 'Share File',
	itemId : 'shareWindow',
	resizable : true,
	height : 500,
	width : 450,
	modal : true,
	filePath : '',
	record : '',
	layout : {
		type : 'fit'
	},
	initComponent : function() {

		var me = this;
		Ext.applyIf(me, {
			items : [ {
				xtype : 'form',
				padding : '10 10 10 10',
				itemId : 'shareFileForm',
				layout : {
					type : 'vbox',
					align : 'stretch'
				},
				items : [ {
					xtype : 'displayfield',
					fieldLabel : 'File Name',
					itemId : 'shareFile_fileName',
					labelSeparator : ''
				}, {
					xtype : 'displayfield',
					fieldLabel : 'File Size',
					itemId : 'shareFile_fileSize',
					labelSeparator : ''
				}, {
					xtype : 'fieldcontainer',
					fieldLabel : 'Select Users',
					labelSeparator : '',
					layout : {
						type : 'hbox'
					},
					items : [ {
						xtype : 'combobox',
						displayField : 'displayName',
						valueField : 'userName',
						queryMode : 'local',
						editable : false,
						itemId : 'shareFile_userCombo'
					}, {
						xtype : 'button',
						margin : '0 0 0 10',
						text : 'Add',
						itemId : 'shareFile_addBtn'
					} ]
				}, {
					xtype : 'shareusergrid',
					itemId : 'shareFile_userGrid',
					title : 'Users',
					padding : '20 0 0 0',
					border : 1,
					height : 200
				} ],
				buttons : [ {
					text : 'Share',
					itemId : 'shareFile_shareBtn'
				} ]
			} ]
		});

		me.callParent(arguments);
	},
	setRecord : function(record) {

		this.record = record;
	},

	getRecord : function() {

		return this.record;
	},
	setFilePath : function(path) {

		this.filePath = path;
	},
	getFilePath : function() {

		return this.filePath;
	}
});
