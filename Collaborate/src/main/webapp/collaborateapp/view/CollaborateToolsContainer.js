Ext.define('CollaborateApp.view.CollaborateToolsContainer', {
	extend : 'Ext.panel.Panel',
	xtype : 'ctoolscontainer',
	layout : {
		type : 'column'
	},
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
		});
		me.callParent(arguments);
	}
});