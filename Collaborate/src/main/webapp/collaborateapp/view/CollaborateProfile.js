Ext.apply(Ext.form.field.VTypes, {
	password : function(val, field) {
		var parentField = field.up('[itemId=' + field.parentFieldItemId + ']');
		var passWordField = parentField.down('[itemId=' + field.passwordFieldItemId + ']');
		return val === passWordField.getValue();
	},
	passwordText : 'Password does not match'
});

Ext.define('CollaborateApp.view.CollaborateProfile', {
	extend : 'Ext.panel.Panel',
	xtype : 'cprofilecontainer',
	itemId : 'cProfileContainer',
	layout : {
		type : 'fit'
	},
	updateProfileInfo : function(profilePanel, store) {
		var cProfile = profilePanel.down('[itemId=cProfile]');
		var record = store.getAt(0);
		cProfile.cProfile_UserInfo_fName.setValue(record.get('firstName'));
		cProfile.cProfile_UserInfo_lName.setValue(record.get('lastName'));
		cProfile.cProfile_UserInfo_UName.setValue(record.get('userName'));
		cProfile.cProfile_EmailMobile_eMail.setValue(record.get('email'));
		cProfile.cProfile_EmailMobile_mobile.setValue(record.get('mobile'));

	},
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [ {
				xtype : 'panel',
				title : 'Profile',
				itemId : 'cProfile',
				border : 1,
				padding : '10 10 10 10',
				bodyPadding : '10 10 10 10',
				layout : {
					type : 'vbox',
					align : 'stretch'
				},
				items : [ {
					xtype : 'fieldset',
					title : 'User Information',
					layout : {
						type : 'vbox',
						align : 'stretch'
					},
					items : [ {
						xtype : 'displayfield',
						labelWidth : 110,
						fieldLabel : 'First Name',
						labelSeparator : '',
						itemId : 'cProfile_UserInfo_fName'
					}, {
						xtype : 'displayfield',
						labelWidth : 110,
						fieldLabel : 'Last Name',
						labelSeparator : '',
						itemId : 'cProfile_UserInfo_lName'
					}, {
						xtype : 'displayfield',
						labelWidth : 110,
						fieldLabel : 'Username',
						labelSeparator : '',
						itemId : 'cProfile_UserInfo_UName'
					} ]
				}, {
					xtype : 'fieldset',
					title : 'Change Password',
					layout : {
						type : 'vbox',
						align : 'stretch'
					},
					items : [ {
						xtype : 'textfield',
						labelWidth : 110,
						fieldLabel : 'Current Password',
						labelSeparator : '',
						inputType : 'password',
						itemId : 'cProfile_ChangePwd_currPwd'
					}, {
						xtype : 'textfield',
						labelWidth : 110,
						fieldLabel : 'New Password',
						regex : new RegExp('((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})'),
						regexText : '* Password should be minimum 6-20 characters. <br> * Must contain symbols @#$%. <br> *  Must contain one lowercase character.<br> * Must contain one uppercase character.',
						labelSeparator : '',
						msgTarget: 'side',
						inputType : 'password',
						enableKeyEvents : true,
						itemId : 'cProfile_ChangePwd_newPwd'
					}, {
						xtype : 'textfield',
						labelWidth : 110,
						fieldLabel : 'Retype Password',
						labelSeparator : '',
						inputType : 'password',
					    vtype : 'password',
						passwordFieldItemId : 'cProfile_ChangePwd_newPwd',
						parentFieldItemId : 'cProfile',
						enableKeyEvents : true,
						itemId : 'cProfile_ChangePwd_reTypePwd'
					}, {
						xtype : 'fieldcontainer',
						layout : {
							type : 'hbox',
							pack : 'end'
						},
						items : [ {
							xtype : 'button',
							text : 'Update',
							disabled : true,
							margin : '0 10 0 0',
							itemId : 'cProfile_ChangePwd_updatePwdBtn'
						}, {
							xtype : 'button',
							text : 'Cancel',
							disabled : true,
							itemId : 'cProfile_ChangePwd_cancelBtn'
						} ]
					} ]
				}, {
					xtype : 'fieldset',
					title : 'Email/Mobile',
					layout : {
						type : 'vbox',
						align : 'stretch'
					},
					items : [ {
						xtype : 'textfield',
						labelWidth : 110,
						fieldLabel : 'Email',
						labelSeparator : '',
						itemId : 'cProfile_EmailMobile_eMail'
					}, {
						xtype : 'textfield',
						labelWidth : 110,
						fieldLabel : 'Mobile',
						labelSeparator : '',
						itemId : 'cProfile_EmailMobile_mobile'
					}, {
						xtype : 'fieldcontainer',
						layout : {
							type : 'hbox',
							pack : 'end'
						},
						items : [ {
							xtype : 'button',
							text : 'Update',
							disabled : true,
							margin : '0 10 0 0',
							itemId : 'cProfile_EmailMobile_updateEMBtn'
						}, {
							xtype : 'button',
							text : 'Cancel',
							disabled : true,
							itemId : 'cProfile_EmailMobile_cancelBtn'
						} ]
					} ]
				} ]
			} ]
		});

		me.callParent(arguments);
	}

});