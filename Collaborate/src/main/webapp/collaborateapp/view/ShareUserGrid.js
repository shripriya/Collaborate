Ext.define('CollaborateApp.view.ShareUserGrid', {
	extend : 'Ext.grid.Panel',
	xtype : 'shareusergrid',
	rowLines : true,
	columnLines : true,
	initialRecordCount : 0,
	columns : [ {
		text : 'User',
		dataIndex : 'displayName',
		flex : 0.4
	}, {
		text : 'Username',
		dataIndex : 'userName',
		flex : 0.3
	}, {
		xtype : 'actioncolumn',
		flex : 0.15,
		align : 'center',
		items : [ {
			iconCls : 'removeIcon',
			tooltip : 'Remove User',
			handler : function(grid, rowIndex, colIndex, item, e, record) {

				var shareFileForm = Ext.ComponentQuery.query('form[itemId=shareFileForm]')[0];
				var fileName = shareFileForm.down('[itemId=shareFile_fileName]');
				var store = Ext.getStore('userInfoStore');
				var recordCount = '' + grid.getStore().count();
				var isNewRecord = record.get('isNewRecord');
				if (!isNewRecord) {
					shareFileForm.userGrid.setLoading("Unsharing File");
					CollaborateUtil.callService('FileSharingService', 'POST', {
						fileName : fileName.getValue(),
						sharedWith : record.get('userName'),
						userName : store.getAt(0).get('userName'),
						recordCount : recordCount,
						requestType : 'UNSHARE_FILE'
					}, function(RESP) {

						shareFileForm.userGrid.setLoading(false);
						if (RESP.REG_RESP.success) {
							grid.getStore().remove(record);
						} else {
							Ext.Msg.show({
								title : 'Collaborate',
								msg : RESP.REG_RESP.msg,
								icon : Ext.Msg.ERROR
							});
						}
					});
				} else {
					grid.getStore().remove(record);
				}
			},
			isDisabled : function(view, rowIdx, colIdx, item, record) {

			},
			scope : this
		} ]
	} ],
	getInitialRecordCount : function() {

		return this.initialRecordCount;
	},
	setInitialRecordCount : function(count) {

		this.initialRecordCount = count;
	}
});