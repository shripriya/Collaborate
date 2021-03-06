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
		type : 'fit'
	},
	items : [ {
		title : 'Login',
		xtype : 'panel',
		width : 550,
		layout : {
			type : 'hbox',
			pack : 'center',
			padding : '100 0 0 0'
		},
		items : [ {
			xtype : 'form',
			title : 'Collaborate Login',
			method : 'POST',
			url : URL.loginURL,
			itemId : 'loginForm',
			items : [ {
				xtype : 'textfield',
				fieldLabel : 'Username',
				padding : '20 10 0 10',
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
	} ]
});
