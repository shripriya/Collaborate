Ext.define('CollaborateApp.view.Main', {
	extend : 'Ext.container.Container',
	xtype : 'main',
	itemId : 'mainPanel',
	layout : {
		type : 'card'
	},
	initComponent : function() {

		var me = this;
		Ext.applyIf(me, {
			items : [ {
				xtype : 'ctoolscontainer',
				itemId : 'cTools_Tools',
				items : [ {
					xtype : 'container',
					columnWidth : 0.50,
					padding : '10 10 10 10',
					layout : {
						type : 'vbox',
						align : 'stretch'
					},
					items : [ {
						xtype : 'filesgrid',
						itemId : 'filesGrid',
						title : 'User Repository',
						rootVisible : true,
						root : {
							text : 'Files',
							expanded : true
						},
						tools : [ {
							type : 'refresh',
							tooltip : 'Refresh',
							itemId : 'filesGrid_Refresh'
						}, {
							type : 'expand',
							tooltip : 'Expand Tree',
							itemId : 'filesGrid_Expand'
						}, {
							type : 'collapse',
							tooltip : 'Collapse Tree',
							itemId : 'filesGrid_Collapse'
						} ],
						viewConfig : {
							plugins : {
								ptype : 'treeviewdragdrop',
								appendOnly : true,
								dragText : '{0} file selected'
							},
							listeners : {
								beforedrop : function(node, src, dest, dropPosition, eOpts) {

									var fileGridRefresh = Ext.ComponentQuery.query('tool[itemId=filesGrid_Refresh]');
									var sourcePath = src.records[0].data.path;
									var destPath = dest.get('path');
									var root = '' + dest.data.root;

									CollaborateUtil.callPOSTService('moveFile', {
										destPath : destPath,
										srcPath : sourcePath,
										root : root
									}, function(RESP) {

										if (RESP !== null) {
											if (RESP.success) {
												fileGridRefresh[0].fireEvent('click', fileGridRefresh[0]);
											} else {
												if (RESP.statusCode == 99) {
													fileGridRefresh[0].fireEvent('click', fileGridRefresh[0]);
													Ext.Msg.show({
														title : 'Collaborate',
														msg : RESP.msg,
														icon : Ext.Msg.ERROR
													});
												} else if (RESP.statusCode == 98) {
													window.location = RESP.redirectURL;
												}
											}
										}
									});

								}
							}
						},
						height : 300
					} ]

				}, {
					xtype : 'container',
					columnWidth : 0.50,
					padding : '10 10 10 10',
					items : []
				} ]
			}, {
				xtype : 'ctoolscontainer',
				itemId : 'cTools_Profile',
				items : [ {
					xtype : 'cprofilecontainer',
					columnWidth : 0.35
				} ]
			} ]
		});
		me.callParent(arguments);
	}
});