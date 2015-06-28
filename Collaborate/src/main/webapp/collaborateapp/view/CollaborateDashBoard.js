Ext.define('CollaborateApp.view.CollaborateDashBoard', {
	extend : 'Ext.container.Container',
	xtype : 'dashboard',
	itemId : 'collaborateDB',
	layout : {
		type : 'border'
	},
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [ {
				xtype : 'component',
				height : 50,
				region : 'north',
				html : 'Collaborate',
				cls : 'app-header'
			}, {
				xtype : 'cmenubar',
				region : 'west',
				width : 150
			}, {
				xtype : 'main',
				region : 'center'
			} ]
		});
		me.callParent(arguments);
	}
});