Ext.define('CollaborateApp.view.FilesGrid', {
	extend : 'Ext.tree.Panel',
	xtype : 'filesgrid',
	border : 1,
	rowLines : true,
	columnLines : true,
	border : 1,
	columns : [ {
		xtype : 'treecolumn',
		text : 'File Name',
		dataIndex : 'name',
		flex : 0.5,
		renderer : function(val, meta, record) {			
			var fileName = record.get('name');
			meta.tdAttr = 'data-qtip="' + fileName + '"';

			return val;
		}
	}, {
		text : 'Size',
		dataIndex : 'size',
		flex : 0.2
	}, {
		xtype : 'actioncolumn',
		text : 'Download',
		flex : 0.15,
		align : 'center',
		items : [ {
			iconCls : 'downloadIcon',
			tooltip : 'Download',
			handler : function(grid, rowIndex, colIndex, item, e, record) {

				var form = Ext.create('Ext.form.Panel');
				var fileName = record.get('name');
				var filePath = record.get('path');
				form.submit({
					url : 'collaborateserver/request/process/fileDownload',
					params : {
						filePath : filePath,
						fileName : fileName
					},
					standardSubmit : true,
					method : 'GET'
				});
			},
			isDisabled : function(view, rowIdx, colIdx, item, record) {

				if (!record.data.leaf) {
					item.iconCls = '';
				} else {
					item.iconCls = 'downloadIcon';
				}
				return !record.data.leaf;
			},
			scope : this
		} ]
	}, {
		xtype : 'actioncolumn',
		flex : 0.15,
		align : 'center',
		items : [ {
			iconCls : 'shareIcon',
			tooltip : 'Share File',
			handler : function(grid, rowIndex, colIndex, item, e, record) {

				var shareWindow = Ext.create('CollaborateApp.view.ShareFile');
				shareWindow.setRecord(record);
				shareWindow.show();
			},
			isDisabled : function(view, rowIdx, colIdx, item, record) {

				if (!record.data.leaf) {
					item.iconCls = '';
				} else {
					item.iconCls = 'shareIcon';
				}
				return !record.data.leaf;
			}
		} ]
	} ]
});