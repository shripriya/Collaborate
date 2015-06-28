Ext.define('CollaborateApp.view.CollaborateMenu', {
	extend : 'Ext.container.Container',
	xtype : 'cmenubar',
	itemId : 'cMenuBar',
	layout : {
		type : 'fit'
	},
	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			items : [ {
				xtype : 'container',
				layout : {
					type : 'vbox',
					align : 'stretch'
				},
				itemId : 'cMenu',
				items : [ {
					xtype : 'label',
					text : '',
					padding : '0 0 10 0',
					itemId : 'welcomeLabel'
				}, {
					xtype : 'menu',
					floating : false,
					plain : false,
					items : [ {
						text : 'Collaborate Tools',
						itemId : 'cMenu_CTools',
						iconCls : 'collaborateToolsIcon'
					}, {
						text : 'Profile',
						itemId : 'cMenu_Profile',
						iconCls : 'profileIcon'
					}, {
						text : 'Logout',
						itemId : 'cMenu_Logout',
						iconCls : 'logoutIcon'
					} ]
				} ]
			} ]
		});
		me.callParent(arguments);
	}
});
