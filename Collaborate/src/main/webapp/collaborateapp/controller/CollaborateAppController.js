Ext.define('CollaborateApp.controller.CollaborateAppController', {
	extend : 'Ext.app.Controller',
	views : [ 'CollaborateDashBoard', 'Main', 'CollaborateToolsContainer', 'FileUpload', 'CollaborateMenu', 'CollaborateProfile', 'FilesGrid', 'FileContextMenu', 'ShareFile', 'ShareUserGrid' ],
	stores : [ 'UserStore', 'FileGridStore' ],
	init : function() {

		this.control({
			'[itemId=collaborateDB]' : {
				afterrender : function(dashboard) {

					dashboard.cMenuBar = dashboard.down('[itemId=cMenuBar]');
					dashboard.mainPanel = dashboard.down('[itemId=mainPanel]');
				}
			},
			'[itemId=mainPanel]' : {
				afterrender : function(mainPanel) {

					mainPanel.cTools_Tools = mainPanel.down('[itemId=cTools_Tools]');
				}
			},
			'[itemId=cTools_Tools]' : {
				afterrender : function(cTools_Tools) {

					cTools_Tools.fileUpload = cTools_Tools.down('[itemId=fileUpload]');
					cTools_Tools.filesGrid = cTools_Tools.down('[itemId=filesGrid]');
				}
			},
			'[itemId=filesGrid]' : {
				afterrender : function(grid) {

					var gridStore = Ext.create('CollaborateApp.store.FileGridStore');
					gridStore.load();
					grid.reconfigure(gridStore);

				},

				cellcontextmenu : function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {

					var menu = Ext.create('CollaborateApp.view.FileContextMenu');
					e.stopEvent();
					menu.parent = grid.up('[itemId=filesGrid]');
					var uploadMenu = menu.queryById('ctxMenuUpload');
					if (record.data.leaf) {
						uploadMenu.setDisabled(true);
					}
					menu.showAt(e.getXY());
					menu.setRecord(record);
				}

			},
			'[itemId=shareGrid]' : {
				afterrender : function(grid) {

					var gridStore = Ext.create('CollaborateApp.store.ShareGridStore');
					grid.reconfigure(gridStore);
				}
			},
			'[itemId=filesGrid_Refresh]' : {
				click : function(refreshTool) {

					var filesGrid = refreshTool.up('[itemId=filesGrid]');
					filesGrid.setLoading(true);
					this.reloadGrid(filesGrid);
				}
			},
			'[itemId=filesGrid_Expand]' : {
				click : function(expandTool) {

					var filesGrid = expandTool.up('[itemId=filesGrid]');
					filesGrid.expandAll();
				}
			},
			'[itemId=filesGrid_Collapse]' : {
				click : function(collapseTool) {

					var filesGrid = collapseTool.up('[itemId=filesGrid]');
					filesGrid.collapseAll();
				}
			},
			'[itemId=uploadBtn]' : {
				click : function(button) {

					var me = this;
					button.setDisabled(true);

					var uploadWindow = button.up('[itemId=fileUploadWindow]');
					var form = button.up('[itemId=fileUploadForm]');
					var fileUpload = form.up('[itemId=fileUpload]');
					var progressBar = fileUpload.down('[itemId=fileUploadProgress]');
					var poller = this.pollUploadProgress(progressBar, 5);
					var cmp = Ext.ComponentQuery.query('treepanel[itemId=filesGrid]');

					uploadWindow.setLoading('Uploading...');
					form.submit({
						url : 'collaborateserver/request/process/fileUpload',
						params : {
							root : fileUpload.record.data.root,
							uploadPath : fileUpload.record.data.path
						},
						success : function(form, action) {

							poller.disconnect();
							me.reloadGrid(cmp[0]);
							uploadWindow.setLoading(false);
						},
						failure : function(form, action) {

							poller.disconnect();
							if (action.result.statusCode == 99) {
								window.setLoading(false);
								uploadWindow.setDisabled(false);
								Ext.Msg.show({
									title : 'Collaborate',
									msg : action.result.msg,
									icon : Ext.Msg.ERROR
								});
							} else if (action.result.statusCode == 98) {

								window.location = action.result.redirectURL;
							}
						}
					});
				}
			},
			'[itemId=fileUploadField]' : {
				change : function(fileupload) {

					var form = fileupload.up('[itemId=fileUploadForm]');
					var fileUpload = form.up('[itemId=fileUpload]');
					var progressBar = fileUpload.down('[itemId=fileUploadProgress]');
					progressBar.reset();
					progressBar.updateText('');

					form.down('[itemId=uploadBtn]').setDisabled(false);
				}
			},
			'[itemId=cMenu_CTools]' : {
				click : function(menu_ctools) {

					var dashboard = menu_ctools.up('[itemId=collaborateDB]');
					dashboard.mainPanel.getLayout().setActiveItem(0);
				}
			},
			'[itemId=cMenu_Profile]' : {
				click : function(menu_profile) {

					var dashboard = menu_profile.up('[itemId=collaborateDB]');
					dashboard.mainPanel.getLayout().setActiveItem(1);
				}
			},
			'[itemId=cMenu_Logout]' : {
				click : function(menu_logout) {

					CollaborateUtil.doLogout({
						logout : true
					}, function(RESP) {

						if (RESP.status_code === '00') {
							window.location = RESP.loginUrl;
						} else {
							Ext.Msg.show({
								title : 'Collaborate',
								msg : RESP.status_msg,
								icon : Ext.Msg.ERROR
							});
						}
					});
				}
			},
			'[itemId=cProfileContainer]' : {
				afterrender : function(cProfileContainer) {

					var cProfile = cProfileContainer.down('[itemId=cProfile]');
					cProfile.cProfile_UserInfo_fName = cProfile.down('[itemId=cProfile_UserInfo_fName]');
					cProfile.cProfile_UserInfo_lName = cProfile.down('[itemId=cProfile_UserInfo_lName]');
					cProfile.cProfile_UserInfo_UName = cProfile.down('[itemId=cProfile_UserInfo_UName]');
					cProfile.cProfile_ChangePwd_currPwd = cProfile.down('[itemId=cProfile_ChangePwd_currPwd]');
					cProfile.cProfile_ChangePwd_newPwd = cProfile.down('[itemId=cProfile_ChangePwd_newPwd]');
					cProfile.cProfile_ChangePwd_reTypePwd = cProfile.down('[itemId=cProfile_ChangePwd_reTypePwd]');
					cProfile.cProfile_ChangePwd_updatePwdBtn = cProfile.down('[itemId=cProfile_ChangePwd_updatePwdBtn]');
					cProfile.cProfile_EmailMobile_eMail = cProfile.down('[itemId=cProfile_EmailMobile_eMail]');
					cProfile.cProfile_EmailMobile_mobile = cProfile.down('[itemId=cProfile_EmailMobile_mobile]');
					cProfile.cProfile_ChangePwd_updateEMBtn = cProfile.down('[itemId=cProfile_ChangePwd_updateEMBtn]');
					cProfileContainer.updateProfileInfo(cProfileContainer, Ext.getStore('userInfoStore'));
				}
			},
			'[itemId=cProfile_ChangePwd_reTypePwd]' : {
				keypress : function(cProfile_ChangePwd_reTypePwd, e) {

					var cProfile = cProfile_ChangePwd_reTypePwd.up('[itemId=cProfile]');

					if (cProfile_ChangePwd_reTypePwd.getActiveError().length == 0) {

					}
				}
			},
			'[itemId=ctxMenuNewFolder]' : {
				click : function(menuItem, e) {

					var menu = menuItem.up('[itemId=fileContextMenu]');
					var refreshBtn = menu.parent.down('[itemId=filesGrid_Refresh]');
					var record = menu.getRecord();

					if (record !== null) {

						Ext.Msg.prompt('Collaborate', 'Folder Name ', function(btn, text) {

							if (btn == 'ok') {
								CollaborateUtil.callPOSTService('createFolder', {
									folderPath : record.data.path,
									folderName : text,
									root : '' + record.data.root
								}, function(RESP) {

									if (RESP !== null) {
										if (RESP.success) {
											refreshBtn.fireEvent('click', refreshBtn);
										} else {
											if (RESP.statusCode == 99) {
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
						});

					}
				}
			},

			'[itemId=ctxMenuDelete]' : {
				click : function(menuItem, e) {

					var menu = menuItem.up('[itemId=fileContextMenu]');
					var refreshBtn = menu.parent.down('[itemId=filesGrid_Refresh]');
					var record = menu.getRecord();
					if (record !== null) {
						if (!record.data.root) {
							menu.parent.setLoading('Deleting');
							CollaborateUtil.callPOSTService('delete', {
								path : record.data.path
							}, function(RESP) {

								menu.parent.setLoading(false);
								if (RESP !== null) {
									if (RESP.success) {
										refreshBtn.fireEvent('click', refreshBtn);
									} else {
										if (RESP.statusCode == 99) {
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
						} else {
							Ext.Msg.show({
								title : 'Collaborate',
								msg : "Root folder cannot be deleted",
								icon : Ext.Msg.INFO
							});
						}
					}
				}
			},
			'[itemId=ctxMenuUpload]' : {
				click : function(menuItem, e) {

					var menu = menuItem.up('[itemId=fileContextMenu]');
					var record = menu.getRecord();

					var window = Ext.create('Ext.window.Window', {
						itemId : 'fileUploadWindow',
						layout : {
							type : 'vbox',
							align : 'stretch'
						},
						title : 'Collaborate',
						resizable : false,
						modal : true,
						width : 350,
						items : [ {
							xtype : 'fileupload',
							itemId : 'fileUpload',
							record : record
						} ]
					});

					window.show();
				}
			},

			'[itemId=shareFile_userCombo]' : {
				afterrender : function(combobox) {

					var store = Ext.getStore('userInfoStore');
					CollaborateUtil.callService('RetrieveUserDetailService', 'POST', {
						userName : store.getAt(0).get('userName'),
						requestType : 'OTHERS'
					}, function(RESP) {

						var store = Ext.create('CollaborateApp.store.UserDetailStore');
						store.add(RESP.REG_RESP.users);
						combobox.bindStore(store);

					});
				}
			},
			'[itemId=shareFileForm]' : {
				afterrender : function(form) {

					form.fileName = form.down('[itemId=shareFile_fileName]');
					form.fileSize = form.down('[itemId=shareFile_fileSize]');
					form.userCombo = form.down('[itemId=shareFile_userCombo]');
					form.userGrid = form.down('[itemId=shareFile_userGrid]');
				}
			},
			'[itemId=shareWindow]' : {
				afterrender : function(window) {

					var me = this;
					var shareFileForm = window.down('[itemId=shareFileForm]');
					var record = window.getRecord();
					window.setFilePath(record.get('path'));
					shareFileForm.fileName.setValue(record.get('name'));
					shareFileForm.fileSize.setValue(record.get('size'));
					me.getShareDetailsByFileName(window);

				}
			},
			'[itemId=shareFile_addBtn]' : {
				click : function(button) {

					var shareFileForm = button.up('[itemId=shareFileForm]');
					var combobox = shareFileForm.userCombo;

					var model = combobox.findRecordByValue(combobox.getValue());
					var userStore = shareFileForm.userGrid.getStore();

					if (userStore.find('userName', model.get('userName')) == -1) {
						var record = model.copy();
						record.set('isNewRecord', true);
						userStore.add(record);
					}

				}
			},
			'[itemId=shareFile_userGrid]' : {
				afterrender : function(grid) {

					var store = Ext.create('CollaborateApp.store.UserDetailStore');
					grid.reconfigure(store);
				}
			},
			'[itemId=shareFile_shareBtn]' : {
				click : function(btn) {

					var me = this;
					var shareFileForm = btn.up('[itemId=shareFileForm]');
					var shareWindow = shareFileForm.up('[itemId=shareWindow]');
					var userStore = shareFileForm.userGrid.getStore();
					var store = Ext.getStore('userInfoStore');
					var totalCount = userStore.count();
					var sharedWith = '';

					for (var i = 0; i < totalCount; i++) {
						var record = userStore.getAt(i);
						if (record.get('isNewRecord')) {
							if (i < totalCount - 1) {
								sharedWith = sharedWith + record.get('userName') + ',';
							} else {
								sharedWith = sharedWith + record.get('userName');
							}
						}
					}

					if (sharedWith !== '') {
						var recordCount = '' + shareFileForm.userGrid.getInitialRecordCount();
						shareFileForm.userGrid.setLoading("Sharing File");
						CollobarateUtil.callService('FileSharingService', 'POST', {
							fileName : shareFileForm.fileName.getValue(),
							filePath : shareWindow.getFilePath(),
							userName : store.getAt(0).get('userName'),
							sharedWith : sharedWith,
							recordCount : recordCount,
							requestType : 'SHARE_FILE'
						}, function(RESP) {

							userStore.removeAll();
							me.getShareDetailsByFileName(shareWindow);
						});
					}
				}
			}

		});
	},

	pollUploadProgress : function(progressBar, pollInterval) {

		var poller = this.getProgressPoller(progressBar, pollInterval);
		Ext.direct.Manager.addProvider(poller);
		return poller;

	},

	getProgressPoller : function(progressBar, pollInterval) {

		var pollProgress = new Ext.direct.PollingProvider({
			type : 'json',
			url : 'collaborateserver/request/process/getProgress',
			interval : pollInterval,
			listeners : {
				data : {
					fn : function(provider, e) {

						progressBar.updateProgress(e.data.actual, e.data.progress + " % ");
						if (e.data.isUploadComplete) {

						}
					}
				}
			}
		});
		return pollProgress;
	},

	reloadGrid : function(grid) {

		grid.getStore().load({
			callback : function(records, operation, success) {

				grid.setLoading(false);
				if (!success) {
					var error = operation.getError();
					if (error == 99) {
						Ext.Msg.show({
							title : 'Collaborate',
							msg : "Exception occured, please contact customer support.",
							icon : Ext.Msg.ERROR
						});
					} else if (error == 98) {
						window.location = '/Collaborate';
					}
				}
			}
		});
	},
	getShareDetailsByFileName : function(shareWindow) {

		var store = Ext.getStore('userInfoStore');
		var shareFileForm = shareWindow.down('[itemId=shareFileForm]');
		CollaborateUtil.callService('FileSharingService', 'POST', {
			fileName : shareFileForm.fileName.getValue(),
			userName : store.getAt(0).get('userName'),
			requestType : 'GET_SHARE_DETAILS_BY_FILENAME'
		}, function(RESP) {

			if (RESP.REG_RESP.success) {
				shareFileForm.userGrid.setLoading(false);
				var sharedFiles = RESP.REG_RESP.SHARED_FILES;
				var length = sharedFiles.length;
				for (var i = 0; i < length; i++) {
					var sharedWith = sharedFiles[i].sharedWith;
					var store = shareFileForm.userCombo.getStore();
					var user = store.findRecord('userName', sharedWith);
					if (user != null) {
						var record = user.copy();
						shareFileForm.userGrid.getStore().add(record);
					}
				}
				shareFileForm.userGrid.setInitialRecordCount(shareFileForm.userGrid.getStore().count());
			} else {
				shareFileForm.userGrid.setLoading(false);
				Ext.Msg.show({
					title : 'Collaborate',
					msg : RESP.REG_RESP.msg,
					icon : Ext.Msg.ERROR
				});
			}
		});
	}
});