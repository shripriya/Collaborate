Ext.define('LoginApp.controller.LoginAppController', {
	extend : 'Ext.app.Controller',
	views : [ 'Login', 'Register' ],
	init : function() {

		this.control({
			'[itemId=loginView]' : {
				afterrender : function(loginView) {

					loginView.loginForm = loginView.down('[itemId=loginForm]');
					loginView.loginUserName = loginView.down('[itemId=loginUserName]');
					loginView.loginPassword = loginView.down('[itemId=loginPassword]');
					loginView.loginBtn = loginView.down('[itemId=loginBtn]');
				}
			},
			'[itemId=register]' : {

				afterrender : function(register) {

					register.registerPanel = register.down('[itemId=registerPanel]');
					register.registerForm = register.down('[itemId=registerForm]');
				}

			},
			'[itemId=loginBtn]' : {
				click : function(button) {

					var loginView = button.up('[itemId=loginView]');
					var loginForm = loginView.loginForm;
					var errLabel = loginForm.down('[itemId=loginErrorLabel]');

					if (loginForm.isValid()) {
						loginForm.setLoading("Logging in....");

						loginForm.submit({
							success : function(form, action) {

								window.location = action.result.dashboardUrl;
							},
							failure : function(form, action) {

								loginForm.setLoading(false);
								errLabel.setText("* " + action.result.status_msg);
							}
						});
					}

				}
			},
			'[itemId=loginPassword]' : {
				keypress : function(loginPassword, e) {

					var loginView = loginPassword.up('[itemId=loginView]');
					var loginBtn = loginView.loginForm.down('[itemId=loginBtn]');
					if (e.getKey() == 13) {
						loginBtn.fireEvent('click', loginBtn);
					}
				}
			},
			'[itemId=registerBtnField]' : {
				click : function(button) {

					var register = button.up('[itemId=register]');
					var registerForm = register.registerPanel.down('[itemId=registerForm]');

					if (registerForm.isValid()) {
						registerForm.setLoading("Registering....");
						CollaborateUtil.callService('RegistrationService', 'POST', registerForm.getValues(), function(RESP) {

							registerForm.setLoading(false);
							if (RESP != null) {
								if (RESP.REG_RESP.status_code === '00') {
									registerForm.getForm().reset();

									Ext.Msg.show({
										title : 'Collaborate',
										msg : RESP.REG_RESP.status_msg,
										icon : Ext.Msg.OK
									});

								} else if (RESP.REG_RESP.status_code === '99') {
									Ext.Msg.show({
										title : 'Collaborate',
										msg : RESP.REG_RESP.status_msg,
										icon : Ext.Msg.ERROR
									});
								}
							} else {
								Ext.Msg.show({
									title : 'Collaborate',
									msg : "Registration Failed",
									icon : Ext.Msg.ERROR
								});
							}
						});
					} else {
						Ext.Msg.show({
							title : 'Collaborate',
							msg : "Please correct the error",
							icon : Ext.Msg.ERROR
						});
					}

				}
			},
		});
	}
});
