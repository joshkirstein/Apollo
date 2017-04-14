//
// Autogenerated by Thrift Compiler (0.9.3)
//
// DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//
var thrift = require('thrift');
var Thrift = thrift.Thrift;
var Q = thrift.Q;

var Apollo_ttypes = require('./Apollo_types')


var ttypes = module.exports = {};
AgentTaskRegistrationException = module.exports.AgentTaskRegistrationException = function(args) {
  Thrift.TException.call(this, "AgentTaskRegistrationException")
  this.name = "AgentTaskRegistrationException"
  this.why = null;
  if (args) {
    if (args.why !== undefined && args.why !== null) {
      this.why = args.why;
    }
  }
};
Thrift.inherits(AgentTaskRegistrationException, Thrift.TException);
AgentTaskRegistrationException.prototype.name = 'AgentTaskRegistrationException';
AgentTaskRegistrationException.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRING) {
        this.why = input.readString();
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

AgentTaskRegistrationException.prototype.write = function(output) {
  output.writeStructBegin('AgentTaskRegistrationException');
  if (this.why !== null && this.why !== undefined) {
    output.writeFieldBegin('why', Thrift.Type.STRING, 1);
    output.writeString(this.why);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

AgentTaskKillException = module.exports.AgentTaskKillException = function(args) {
  Thrift.TException.call(this, "AgentTaskKillException")
  this.name = "AgentTaskKillException"
  this.why = null;
  if (args) {
    if (args.why !== undefined && args.why !== null) {
      this.why = args.why;
    }
  }
};
Thrift.inherits(AgentTaskKillException, Thrift.TException);
AgentTaskKillException.prototype.name = 'AgentTaskKillException';
AgentTaskKillException.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRING) {
        this.why = input.readString();
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

AgentTaskKillException.prototype.write = function(output) {
  output.writeStructBegin('AgentTaskKillException');
  if (this.why !== null && this.why !== undefined) {
    output.writeFieldBegin('why', Thrift.Type.STRING, 1);
    output.writeString(this.why);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

AgentTaskPurgeException = module.exports.AgentTaskPurgeException = function(args) {
  Thrift.TException.call(this, "AgentTaskPurgeException")
  this.name = "AgentTaskPurgeException"
  this.why = null;
  if (args) {
    if (args.why !== undefined && args.why !== null) {
      this.why = args.why;
    }
  }
};
Thrift.inherits(AgentTaskPurgeException, Thrift.TException);
AgentTaskPurgeException.prototype.name = 'AgentTaskPurgeException';
AgentTaskPurgeException.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRING) {
        this.why = input.readString();
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

AgentTaskPurgeException.prototype.write = function(output) {
  output.writeStructBegin('AgentTaskPurgeException');
  if (this.why !== null && this.why !== undefined) {
    output.writeFieldBegin('why', Thrift.Type.STRING, 1);
    output.writeString(this.why);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

ttypes.DEFAULT_DOCKER_LOCATION = '/usr/local/bin/docker';
ttypes.DEFAULT_AGENT_SERVICE_IP = '127.0.0.1';
ttypes.DEFAULT_AGENT_SERVICE_PORT = 9893;