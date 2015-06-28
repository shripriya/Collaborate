var displayName = function(v, record) {

	return record.get('lastName') + ', ' + record.get('firstName') + ' (' + record.get('userName') + ')';
};

Ext.define('CollaborateApp.model.UserModel', {
	extend : 'Ext.data.Model',
	xtype : 'userModel',
	fields : [ {
		name : 'userName',
		type : 'string'
	}, {
		name : 'email',
		type : 'string'
	}, {
		name : 'mobile',
		type : 'string'
	}, {
		name : 'firstName',
		type : 'string'
	}, {
		name : 'lastName',
		type : 'string'
	}, {
		name : 'displayName',
		type : 'string',
		convert : this.displayName
	}, {
		name : 'isNewRecord',
		type : 'boolean',
		defaultValue : false
	} ]
});