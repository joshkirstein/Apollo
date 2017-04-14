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
AgentRegistrationException = module.exports.AgentRegistrationException = function(args) {
  Thrift.TException.call(this, "AgentRegistrationException")
  this.name = "AgentRegistrationException"
  this.why = null;
  if (args) {
    if (args.why !== undefined && args.why !== null) {
      this.why = args.why;
    }
  }
};
Thrift.inherits(AgentRegistrationException, Thrift.TException);
AgentRegistrationException.prototype.name = 'AgentRegistrationException';
AgentRegistrationException.prototype.read = function(input) {
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

AgentRegistrationException.prototype.write = function(output) {
  output.writeStructBegin('AgentRegistrationException');
  if (this.why !== null && this.why !== undefined) {
    output.writeFieldBegin('why', Thrift.Type.STRING, 1);
    output.writeString(this.why);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SchedulerTaskStatusUpdateException = module.exports.SchedulerTaskStatusUpdateException = function(args) {
  Thrift.TException.call(this, "SchedulerTaskStatusUpdateException")
  this.name = "SchedulerTaskStatusUpdateException"
  this.why = null;
  if (args) {
    if (args.why !== undefined && args.why !== null) {
      this.why = args.why;
    }
  }
};
Thrift.inherits(SchedulerTaskStatusUpdateException, Thrift.TException);
SchedulerTaskStatusUpdateException.prototype.name = 'SchedulerTaskStatusUpdateException';
SchedulerTaskStatusUpdateException.prototype.read = function(input) {
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

SchedulerTaskStatusUpdateException.prototype.write = function(output) {
  output.writeStructBegin('SchedulerTaskStatusUpdateException');
  if (this.why !== null && this.why !== undefined) {
    output.writeFieldBegin('why', Thrift.Type.STRING, 1);
    output.writeString(this.why);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SchedulerTaskStatusRequestException = module.exports.SchedulerTaskStatusRequestException = function(args) {
  Thrift.TException.call(this, "SchedulerTaskStatusRequestException")
  this.name = "SchedulerTaskStatusRequestException"
  this.why = null;
  if (args) {
    if (args.why !== undefined && args.why !== null) {
      this.why = args.why;
    }
  }
};
Thrift.inherits(SchedulerTaskStatusRequestException, Thrift.TException);
SchedulerTaskStatusRequestException.prototype.name = 'SchedulerTaskStatusRequestException';
SchedulerTaskStatusRequestException.prototype.read = function(input) {
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

SchedulerTaskStatusRequestException.prototype.write = function(output) {
  output.writeStructBegin('SchedulerTaskStatusRequestException');
  if (this.why !== null && this.why !== undefined) {
    output.writeFieldBegin('why', Thrift.Type.STRING, 1);
    output.writeString(this.why);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SchedulerAgentInfoRequestException = module.exports.SchedulerAgentInfoRequestException = function(args) {
  Thrift.TException.call(this, "SchedulerAgentInfoRequestException")
  this.name = "SchedulerAgentInfoRequestException"
  this.why = null;
  if (args) {
    if (args.why !== undefined && args.why !== null) {
      this.why = args.why;
    }
  }
};
Thrift.inherits(SchedulerAgentInfoRequestException, Thrift.TException);
SchedulerAgentInfoRequestException.prototype.name = 'SchedulerAgentInfoRequestException';
SchedulerAgentInfoRequestException.prototype.read = function(input) {
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

SchedulerAgentInfoRequestException.prototype.write = function(output) {
  output.writeStructBegin('SchedulerAgentInfoRequestException');
  if (this.why !== null && this.why !== undefined) {
    output.writeFieldBegin('why', Thrift.Type.STRING, 1);
    output.writeString(this.why);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SchedulerSnapshotException = module.exports.SchedulerSnapshotException = function(args) {
  Thrift.TException.call(this, "SchedulerSnapshotException")
  this.name = "SchedulerSnapshotException"
  this.why = null;
  if (args) {
    if (args.why !== undefined && args.why !== null) {
      this.why = args.why;
    }
  }
};
Thrift.inherits(SchedulerSnapshotException, Thrift.TException);
SchedulerSnapshotException.prototype.name = 'SchedulerSnapshotException';
SchedulerSnapshotException.prototype.read = function(input) {
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

SchedulerSnapshotException.prototype.write = function(output) {
  output.writeStructBegin('SchedulerSnapshotException');
  if (this.why !== null && this.why !== undefined) {
    output.writeFieldBegin('why', Thrift.Type.STRING, 1);
    output.writeString(this.why);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SchedulerTaskRegistrationException = module.exports.SchedulerTaskRegistrationException = function(args) {
  Thrift.TException.call(this, "SchedulerTaskRegistrationException")
  this.name = "SchedulerTaskRegistrationException"
  this.why = null;
  if (args) {
    if (args.why !== undefined && args.why !== null) {
      this.why = args.why;
    }
  }
};
Thrift.inherits(SchedulerTaskRegistrationException, Thrift.TException);
SchedulerTaskRegistrationException.prototype.name = 'SchedulerTaskRegistrationException';
SchedulerTaskRegistrationException.prototype.read = function(input) {
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

SchedulerTaskRegistrationException.prototype.write = function(output) {
  output.writeStructBegin('SchedulerTaskRegistrationException');
  if (this.why !== null && this.why !== undefined) {
    output.writeFieldBegin('why', Thrift.Type.STRING, 1);
    output.writeString(this.why);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SchedulerTaskKillException = module.exports.SchedulerTaskKillException = function(args) {
  Thrift.TException.call(this, "SchedulerTaskKillException")
  this.name = "SchedulerTaskKillException"
  this.why = null;
  if (args) {
    if (args.why !== undefined && args.why !== null) {
      this.why = args.why;
    }
  }
};
Thrift.inherits(SchedulerTaskKillException, Thrift.TException);
SchedulerTaskKillException.prototype.name = 'SchedulerTaskKillException';
SchedulerTaskKillException.prototype.read = function(input) {
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

SchedulerTaskKillException.prototype.write = function(output) {
  output.writeStructBegin('SchedulerTaskKillException');
  if (this.why !== null && this.why !== undefined) {
    output.writeFieldBegin('why', Thrift.Type.STRING, 1);
    output.writeString(this.why);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

ttypes.DEFAULT_SCHEDULER_SERVICE_IP = '127.0.0.1';
ttypes.DEFAULT_SCHEDULER_AGENT_SERVICE_PORT = 6666;
ttypes.DEFAULT_SCHEDULER_ADMIN_SERVICE_PORT = 43594;