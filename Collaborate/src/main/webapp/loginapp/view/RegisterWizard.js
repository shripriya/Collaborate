Ext.define('LoginApp.view.RegisterWizard', {
	extend : 'Ext.panel.Panel',
	xtype : 'registerwizard',
	itemId: 'registerWizard',
	title : 'Register',
	layout : {
		type : 'hbox',
		pack : 'center'
	},
	initComponent : function() {

		var me = this;
		Ext.applyIf(me, {
			items : [ {
				xtype : 'panel',
				itemId: 'wizardPanel',				
				layout : {
					type : 'card'
				},
				items : [ {
					xtype : 'form',
					padding : '100 0 0 0',
					height: 550,
					width : 800,
					title : 'Register',
					itemId : 'registerForm',
					method : 'POST',
					url : URL.serviceURL + 'RegistrationService',
					layout : {
						type : 'vbox',
						padding : '50 20 30 20'
					},
					items : [ {
						xtype : 'fieldcontainer',
						layout : {
							type : 'hbox'
						},
						items : [ {
							xtype : 'textfield',
							labelSeparator : '',
							fieldLabel : 'First Name',
							labelWidth : 110,
							labelAlign : 'left',
							maxLength : 25,
							maxLengthText : 'Name cannot exceed 25 characters',
							msgTarget : 'side',
							allowBlank : false,
							name : 'firstName',
							vtype : 'alpha',
							itemId : 'firstNameField'
						}, {
							xtype : 'textfield',
							fieldLabel : 'Last Name',
							labelSeparator : '',
							padding : '0 0 0 20',
							maxLength : 25,
							maxLengthText : 'Name cannot exceed 25 characters',
							msgTarget : 'side',
							labelWidth : 110,
							allowBlank : false,
							labelAlign : 'left',
							name : 'lastName',
							vtype : 'alpha',
							itemId : 'lastNameField'
						} ]
					}, {
						xtype : 'radiogroup',
						fieldLabel : 'Sex',
						padding : '20 0 0 0',
						labelWidth : 110,
						labelAlign : 'left',
						labelSeparator : '',
						itemId : 'sexField',
						items : [ {
							boxLabel : 'Male',
							width : 150,
							name : 'sex',
							checked : true,
							inputValue : 'Male'
						}, {
							boxLabel : 'Female',
							width : 150,
							name : 'sex',
							inputValue : 'Female'
						} ]
					}, {
						xtype : 'textfield',
						padding : '20 0 0 0',
						fieldLabel : 'Email',
						labelWidth : 110,
						labelAlign : 'left',
						width : 350,
						allowBlank : false,
						labelSeparator : '',
						vtype : 'email',
						msgTarget : 'side',
						vtypeText : 'Invalid email id',
						name : 'email',
						itemId : 'emailField'
					}, {
						xtype : 'textfield',
						padding : '20 0 0 0',
						fieldLabel : 'Mobile',
						labelWidth : 110,
						allowBlank : false,
						labelAlign : 'left',
						maskRe : /[0-9.]/,
						maxLength : 10,
						minLength : 10,
						minLengthText : 'Ivalid mobile number',
						maxLengthText : 'Ivalid mobile number',
						msgTarget : 'side',
						width : 350,
						labelSeparator : '',
						name : 'mobile',
						itemId : 'mobileField'
					}, {
						xtype : 'textfield',
						padding : '20 0 0 0',
						labelWidth : 110,
						labelAlign : 'left',
						fieldLabel : 'Username',
						width : 350,
						labelSeparator : '',
						msgTarget : 'side',
						name : 'userName',
						allowBlank : false,
						vtype : 'username',
						itemId : 'userNameField'
					}, {
						xtype : 'textfield',
						padding : '20 0 0 0',
						labelWidth : 110,
						labelAlign : 'left',
						fieldLabel : 'Password',
						inputType : 'password',
						regex : new RegExp('((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})'),
						regexText : '* Password should be minimum 6-20 characters. <br> * Must contain symbols @#$%. <br> *  Must contain one lowercase character.<br> * Must contain one uppercase character.',
						allowBlank : false,
						msgTarget : 'side',
						width : 350,
						labelSeparator : '',
						name : 'password',
						itemId : 'passWordField'
					}, {
						xtype : 'textfield',
						padding : '20 0 0 0',
						fieldLabel : 'Retype Password',
						labelWidth : 110,
						labelAlign : 'left',
						msgTarget : 'side',
						inputType : 'password',
						allowBlank : false,
						width : 350,
						labelSeparator : '',
						vtype : 'password',
						name : 'retypedPassword',
						msgTarget : 'side',
						passwordFieldItemId : 'passWordField',
						parentFieldItemId : 'registerForm',
						itemId : 'confirmPwdField'
					} ]
				}, {
					xtype : 'form',
					padding : '100 0 0 0',
					height: 550,
					width : 800,
					layout : {
						type : 'vbox',
						pack: 'center',
						padding : '50 20 30 50'
					},
					title : 'Verify Email',
					itemId : 'verifyEmailForm',
					items : [ {
						xtype : 'displayfield',
						padding : '20 0 0 0',
						fieldLabel : 'Email',
						labelWidth : 110,
						labelAlign : 'left',
						width : 350,
						allowBlank : false,
						labelSeparator : '',
						name : 'verifyEmail',
						itemId : 'verifyEmailField'
					}, {
						xtype : 'textfield',
						padding : '20 0 0 0',
						fieldLabel : 'Verification Code',
						labelWidth : 110,
						labelAlign : 'left',
						width : 350,
						allowBlank : false,
						labelSeparator : '',
						name : 'vcode',
						itemId : 'vCodeField'
					} ]					
				} ],
				buttons : [ {
					text : 'Previous',
					hidden : true,
					itemId : 'regPrevBtn'
				}, '->', {
					text : 'Register',
					itemId : 'registerBtnField'
				} ]
			} ]
		});
		me.callParent(arguments);
	}
});