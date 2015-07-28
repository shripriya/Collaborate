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
						type : 'hbox'
					},
					items : [ {
						xtype : 'form',
						layout : {
							type : 'vbox',
							padding : '20 20 0 20'
						},
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
						}, {
							xtype : 'container',
							layout : {
								type : 'hbox',
								pack : 'end'
							},
							items : [ {
								xtype : 'button',
								text : 'Verify'
							} ]
						} ]
					} ]
				} ]
			} ]
		});
	}
});
