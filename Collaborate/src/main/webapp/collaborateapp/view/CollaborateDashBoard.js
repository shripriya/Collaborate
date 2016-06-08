Ext.define('CollaborateApp.view.CollaborateDashBoard', {
	extend : 'Ext.container.Container',
	xtype : 'dashboard',
	itemId : 'collaborateDB',
	layout : {
		type : 'fit'
	},
	initComponent : function() {

		var me = this;
		Ext.applyIf(me, {
			items : [ {
				xtype : 'main',
				region : 'center'
			} ]
		});
		me.callParent(arguments);
	}
});