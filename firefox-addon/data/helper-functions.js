"use strict";
function help(object) {
	if ( typeof object === "string") {
		return new StringHelper(object);
	}
	if (Array.isArray(object)) {
		return new ArrayHelper(object);
	}
	throw Error("Don't know what to do with object " + object);
}

function ArrayHelper(array) {
	this.array = array;
}

ArrayHelper.prototype.remove = function(item) {
	var i = this.array.indexOf(item);
	if (i != -1) {
		this.array.splice(i, 1);
	}
};

ArrayHelper.prototype.add = function(item) {
	this.array.push(item);
};

ArrayHelper.prototype.contains = function(item) {
	return this.array.indexOf(item) != -1;
};

function StringHelper(string) {
	this.string = string;
}

StringHelper.prototype.startsWith = function(substring) {
	return this.string.indexOf(substring) === 0;
};
