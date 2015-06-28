/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
Ext.apply(Ext.form.field.VTypes, {
	password : function(val, field) {
		var parentField = field.up('[itemId=' + field.parentFieldItemId + ']');
		var passWordField = parentField.down('[itemId=' + field.passwordFieldItemId + ']');
		return val === passWordField.getValue();
	},
	passwordText : 'Password does not match',
	username : function(val, field) {

		if (val.length < 6) {
			this.usernameText = 'Username should be minimum of 6 characters';
			return false;
		} else if (val.length > 15) {
			this.usernameText = 'Username should not exceed 15 characters';
			return false;
		} else if (!(/^[a-z0-9_-]{6,15}$/.test(val))) {
			this.usernameText = 'Username should not contain special characters';
			return false;
		} else {
			return true;
		}

	},
	usernameText : 'Username should not contain any special characters'
});

Ext.define('LoginApp.view.Login', {
	extend : 'Ext.panel.Panel',
	xtype : 'login',
	itemId : 'loginView',	
	layout : {
		type : 'accordion',
		titleCollapse: false,
        animate: true,
        activeOnTop: true
	},		
	items : [ {
		title: 'Login',
		xtype : 'panel',		
		width : 550,
		layout : {
			type : 'hbox',
			pack : 'center',
			padding: '100 0 0 0'
		},
		items : [ {
			xtype : 'form',
			height : 180,			
			width : 400,
			title : 'Collaborate Login',
			method : 'POST',
			url : URL.loginURL,
			itemId : 'loginForm',
			layout : {
				type : 'vbox',
				align : 'stretch',
				pack : 'end'
			},
			items : [ {
				xtype : 'textfield',
				fieldLabel : 'Username',
				padding : '0 10 0 10',
				labelSeparator : '',
				name : 'userName',
				vtype : 'username',
				itemId : 'loginUserName'
			}, {
				xtype : 'textfield',
				fieldLabel : 'Password',
				enableKeyEvents : true,
				labelSeparator : '',
				padding : '0 10 0 10',
				inputType : 'password',
				name : 'password',
				itemId : 'loginPassword'
			}, {
				xtype : 'label',
				padding : '10 10 10 10',
				itemId : 'loginErrorLabel',
				text : '  ',
				style : {
					color : 'red'
				}
			} ],
			buttons : [ {
				text : 'Login',
				itemId : 'loginBtn'
			} ]

		} ]
	}, {
		title: 'Register',
		xtype : 'panel',
		flex : 1.25,
		layout : {
			type : 'hbox',
			pack : 'center'
		},
		items : [ {
			xtype : 'form',
			padding : '100 0 0 0',
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
			} ],
			buttons : [ {
				text : 'Register',
				itemId : 'registerBtnField'
			}]
		} ]
	} ]
});
