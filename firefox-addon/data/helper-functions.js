if ( typeof Array.prototype.remove === 'undefined') {
	Array.prototype.remove = function(item) {
		var i = this.indexOf(item);
		if (i != -1) {
			this.splice(i, 1);
		}
	};
}

if ( typeof Array.prototype.add === 'undefined') {
	Array.prototype.add = function(item) {
		this.push(item);
	};
}

if ( typeof Array.prototype.contains === 'undefined') {
	Array.prototype.contains = function(item) {
		return this.indexOf(item) != -1;
	};
}