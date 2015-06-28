Ext.Loader.setConfig({
	enabled : true
});

Ext.application({
	name : "LoginApp",
	appFolder : "loginapp",
	controllers : [ 'LoginAppController' ],
	launch : function() {
		this.mainView = Ext.create("Ext.container.Container", {
			renderTo : 'loginPanel',
			layout : 'fit',
			itemId : 'mainView',
			items : [ {
				xtype : 'container',
				itemId : 'mainContainer',
				layout : {
					type : 'anchor'
				},
				items : [ {
					xtype : 'login'
				} ]
			} ]
		});
	}
});
