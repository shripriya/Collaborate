var renderLogin = function(renderTo) {
	var me = this;
	console.log(renderTo);
	Ext.Loader.setConfig({
		enabled : true
	});

	Ext.application({
		name : "LoginApp",
		appFolder : "loginapp",
		controllers : [ 'LoginAppController' ],
		launch : function() {
			this.mainView = Ext.create("Ext.container.Viewport", {
				renderTo : 'renderTo',
				layout : 'fit',
				itemId : 'mainView',
				autoCreateViewPort : false,
				items : [ {
					xtype : 'container',
					itemId : 'mainContainer',
					layout : {
						type : 'fit'
					},
					items : [ {
						xtype : 'login'
					} ]
				} ]
			});
		}
	});
}