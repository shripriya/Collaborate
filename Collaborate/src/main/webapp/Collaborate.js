Ext.Loader.setConfig({
	enabled : true
});

Ext.application({
	name : "CollaborateApp",
	appFolder : "collaborateapp",
	controllers : [ 'CollaborateAppController' ],
	launch : function() {

		var me = this;
		CollaborateUtil.callPOSTService('retrieveUserInfo', {
			retrieveUser : true
		}, function(RESP) {

			var userStore = Ext.create('CollaborateApp.store.UserStore');
			userStore.add(RESP.userInfo);

			me.mainView = Ext.create("Ext.container.Viewport", {
				renderTo : Ext.getBody(),
				layout : 'fit',
				itemId : 'mainView',
				items : [ {
					xtype : 'container',
					itemId : 'mainContainer',
					layout : {
						type : 'fit'
					// pack: 'center'
					},
					items : [ {
						xtype : 'dashboard'
					} ]
				} ]
			});

		});

	}
});
