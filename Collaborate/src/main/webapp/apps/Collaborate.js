var renderCollaborate = function(renderTo) {

	var me = this;
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
				Ext.create("Ext.container.Container", {
					renderTo : renderTo,
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
							xtype : 'dashboard'
						} ]
					} ]
				});

			});

		}
	});
}