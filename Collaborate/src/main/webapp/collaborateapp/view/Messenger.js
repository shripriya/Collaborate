Ext.define('CollaborateApp.view.Messenger', {
	extend : 'Ext.panel.Panel',
	title : 'Messenger',
	itemId : 'messenger',
	xtype : 'messenger',
	cls : 'x-portlet',
	closable : false,
	collapsible : true,
	frame : true,
	isPortlet : true,
	draggable : {
		moveOnDrag : false
	},
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
					xtype : 'textfield',
					fieldLabel : 'Message',
					labelAlign : 'top',
					labelSeparator : '',
					enableKeyEvents : true,
					flex : 0.15,
					itemId : 'msngrMsg'
				}, {
					xtype : 'htmleditor',
					flex : 0.85,
					enableColors : false,
					enableAlignments : false,
					enableFont : false,
					enableFontSize : false,
					enableFormat : false,
					enableLists : false,
					enableLinks : false,
					editable : false,
					enableSourceEdit : false,
					readOnly : true,
					itemId : 'msngrTxtArea'
				} ]
			} ]
		});

		me.callParent(arguments);
	}
});