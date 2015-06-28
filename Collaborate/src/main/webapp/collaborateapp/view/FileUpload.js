Ext.define('CollaborateApp.view.FileUpload', {
	extend : 'Ext.panel.Panel',
	title : 'File Upload',
	xtype : 'fileupload',
	layout : {
		type : 'fit'
	},
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [ {
				xtype : 'form',
				layout : {
					type : 'vbox',
					align : 'stretch'
				},
				items : [ {
					xtype : 'filefield',
					name : 'file',
					itemId : 'fileUploadField',
					padding : '20 10 20 10',
					msgTarget : 'under',
					labelSeparator : '',
					buttonText : 'Select File'
				} ],
				buttons : [ {
					text : 'Upload',
					itemId : 'uploadBtn',
					disabled : 'true'
				} ],
				itemId : 'fileUploadForm'
			}, {
				xtype : 'progressbar',
				itemId : 'fileUploadProgress',
				animate : true,
				style : {
					font : 'Cambria'
				},
				height : 125
			} ]
		});
		me.callParent(arguments);
	}

});