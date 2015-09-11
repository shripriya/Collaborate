Ext.Loader.setConfig({
	enabled : true
});

Ext.application({
	name : "Verify",
	launch : function() {

		this.mainView = Ext.create('Ext.container.Viewport', {
			itemId : 'verifyEmail',
			layout : {
				type : 'fit'
			},
			renderTo : Ext.getBody(),
			items : [ {
				xtype : 'container',
				layout : {
					type : 'vbox',
					align : 'stretch'
				},
				items : [ {
					xtype : 'component',
					height : 50,
					html : 'Collaborate',
					cls : 'app-header'
				}, {
					xtype : 'container',
					layout : {
						type : 'hbox',
						pack : 'center',
						padding : '100 0 0 0'
					},
					items : [ {
						xtype : 'form',
						layout : {
							type : 'vbox',
							padding : '20 20 0 20'
						},
						height : 200,
						width : 400,
						itemId : 'verifyEmailForm',
						items : [ {
							xtype : 'textfield',
							padding : '20 0 0 0',
							fieldLabel : 'Username',
							labelWidth : 110,
							labelAlign : 'left',
							width : 350,
							msgTarget : 'side',
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
							msgTarget : 'side',
							allowBlank : false,
							labelSeparator : '',
							name : 'vcode',
							itemId : 'vCodeField'
						} ],
						buttons : [ {
							text : 'Verify Account',
							itemId : 'verifyAccBtn'
						},{
							text : 'Resend Email',
							itemId : 'resendEmail'
						} ]
					} ]
				} ]
			} ]
		});
	}
});
