Ext.define('LoginApp.controller.LoginAppController', {
	extend : 'Ext.app.Controller',
	views : [ 'Login', 'RegisterWizard' ],
	init : function() {

		this.control({
			'[itemId=loginView]' : {
				afterrender : function(loginView) {

					loginView.loginForm = loginView.down('[itemId=loginForm]');
					loginView.loginUserName = loginView.down('[itemId=loginUserName]');
					loginView.loginPassword = loginView.down('[itemId=loginPassword]');
					loginView.loginBtn = loginView.down('[itemId=loginBtn]');
					loginView.registerForm = loginView.down('[itemId=registerForm]');
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

					var wizPanel = button.up('[itemId=wizardPanel]');
					var prevButton = wizPanel.down('[itemId=regPrevBtn]');
					var loginView = button.up('[itemId=loginView]');
					if (button.getText() == 'Register') {
						if (loginView.registerForm.isValid()) {
							loginView.registerForm.setLoading("Registering....");
							CollaborateUtil.callService('RegistrationService', 'POST', loginView.registerForm.getValues(), function(RESP) {

								loginView.registerForm.setLoading(false);
								if (RESP != null) {
									if (RESP.REG_RESP.status_code === '00') {
										loginView.registerForm.getForm().reset();

										var layout = wizPanel.getLayout()
										layout['next']();
										prevButton.setVisible(true);
										button.setText('Verify');

										/*
										 * Ext.Msg.show({ title : 'Collaborate',
										 * msg : RESP.REG_RESP.status_msg, icon :
										 * Ext.Msg.OK });
										 */
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
				}
			},
		});
	}
});
