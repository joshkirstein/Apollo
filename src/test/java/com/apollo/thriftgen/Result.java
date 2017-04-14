/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.apollo.thriftgen;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
public class Result extends org.apache.thrift.TUnion<Result, Result._Fields> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Result");
  private static final org.apache.thrift.protocol.TField AGENT_REGISTER_RESULT_FIELD_DESC = new org.apache.thrift.protocol.TField("agentRegisterResult", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField SCHEDULER_REGISTER_RESULT_FIELD_DESC = new org.apache.thrift.protocol.TField("schedulerRegisterResult", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField SCHEDULER_VERIFY_TASKS_RESULT_FIELD_DESC = new org.apache.thrift.protocol.TField("schedulerVerifyTasksResult", org.apache.thrift.protocol.TType.STRUCT, (short)3);
  private static final org.apache.thrift.protocol.TField SCHEDULER_GET_TASK_PORTS_RESULT_FIELD_DESC = new org.apache.thrift.protocol.TField("schedulerGetTaskPortsResult", org.apache.thrift.protocol.TType.STRUCT, (short)4);
  private static final org.apache.thrift.protocol.TField SCHEDULER_GET_STATE_RESULT_FIELD_DESC = new org.apache.thrift.protocol.TField("schedulerGetStateResult", org.apache.thrift.protocol.TType.STRUCT, (short)5);

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    AGENT_REGISTER_RESULT((short)1, "agentRegisterResult"),
    SCHEDULER_REGISTER_RESULT((short)2, "schedulerRegisterResult"),
    SCHEDULER_VERIFY_TASKS_RESULT((short)3, "schedulerVerifyTasksResult"),
    SCHEDULER_GET_TASK_PORTS_RESULT((short)4, "schedulerGetTaskPortsResult"),
    SCHEDULER_GET_STATE_RESULT((short)5, "schedulerGetStateResult");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // AGENT_REGISTER_RESULT
          return AGENT_REGISTER_RESULT;
        case 2: // SCHEDULER_REGISTER_RESULT
          return SCHEDULER_REGISTER_RESULT;
        case 3: // SCHEDULER_VERIFY_TASKS_RESULT
          return SCHEDULER_VERIFY_TASKS_RESULT;
        case 4: // SCHEDULER_GET_TASK_PORTS_RESULT
          return SCHEDULER_GET_TASK_PORTS_RESULT;
        case 5: // SCHEDULER_GET_STATE_RESULT
          return SCHEDULER_GET_STATE_RESULT;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.AGENT_REGISTER_RESULT, new org.apache.thrift.meta_data.FieldMetaData("agentRegisterResult", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, AgentRegisterResult.class)));
    tmpMap.put(_Fields.SCHEDULER_REGISTER_RESULT, new org.apache.thrift.meta_data.FieldMetaData("schedulerRegisterResult", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, SchedulerRegisterResult.class)));
    tmpMap.put(_Fields.SCHEDULER_VERIFY_TASKS_RESULT, new org.apache.thrift.meta_data.FieldMetaData("schedulerVerifyTasksResult", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, SchedulerVerifyTasksResult.class)));
    tmpMap.put(_Fields.SCHEDULER_GET_TASK_PORTS_RESULT, new org.apache.thrift.meta_data.FieldMetaData("schedulerGetTaskPortsResult", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, SchedulerGetTaskPortsResult.class)));
    tmpMap.put(_Fields.SCHEDULER_GET_STATE_RESULT, new org.apache.thrift.meta_data.FieldMetaData("schedulerGetStateResult", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, SchedulerGetStateResult.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Result.class, metaDataMap);
  }

  public Result() {
    super();
  }

  public Result(_Fields setField, Object value) {
    super(setField, value);
  }

  public Result(Result other) {
    super(other);
  }
  public Result deepCopy() {
    return new Result(this);
  }

  public static Result agentRegisterResult(AgentRegisterResult value) {
    Result x = new Result();
    x.setAgentRegisterResult(value);
    return x;
  }

  public static Result schedulerRegisterResult(SchedulerRegisterResult value) {
    Result x = new Result();
    x.setSchedulerRegisterResult(value);
    return x;
  }

  public static Result schedulerVerifyTasksResult(SchedulerVerifyTasksResult value) {
    Result x = new Result();
    x.setSchedulerVerifyTasksResult(value);
    return x;
  }

  public static Result schedulerGetTaskPortsResult(SchedulerGetTaskPortsResult value) {
    Result x = new Result();
    x.setSchedulerGetTaskPortsResult(value);
    return x;
  }

  public static Result schedulerGetStateResult(SchedulerGetStateResult value) {
    Result x = new Result();
    x.setSchedulerGetStateResult(value);
    return x;
  }


  @Override
  protected void checkType(_Fields setField, Object value) throws ClassCastException {
    switch (setField) {
      case AGENT_REGISTER_RESULT:
        if (value instanceof AgentRegisterResult) {
          break;
        }
        throw new ClassCastException("Was expecting value of type AgentRegisterResult for field 'agentRegisterResult', but got " + value.getClass().getSimpleName());
      case SCHEDULER_REGISTER_RESULT:
        if (value instanceof SchedulerRegisterResult) {
          break;
        }
        throw new ClassCastException("Was expecting value of type SchedulerRegisterResult for field 'schedulerRegisterResult', but got " + value.getClass().getSimpleName());
      case SCHEDULER_VERIFY_TASKS_RESULT:
        if (value instanceof SchedulerVerifyTasksResult) {
          break;
        }
        throw new ClassCastException("Was expecting value of type SchedulerVerifyTasksResult for field 'schedulerVerifyTasksResult', but got " + value.getClass().getSimpleName());
      case SCHEDULER_GET_TASK_PORTS_RESULT:
        if (value instanceof SchedulerGetTaskPortsResult) {
          break;
        }
        throw new ClassCastException("Was expecting value of type SchedulerGetTaskPortsResult for field 'schedulerGetTaskPortsResult', but got " + value.getClass().getSimpleName());
      case SCHEDULER_GET_STATE_RESULT:
        if (value instanceof SchedulerGetStateResult) {
          break;
        }
        throw new ClassCastException("Was expecting value of type SchedulerGetStateResult for field 'schedulerGetStateResult', but got " + value.getClass().getSimpleName());
      default:
        throw new IllegalArgumentException("Unknown field id " + setField);
    }
  }

  @Override
  protected Object standardSchemeReadValue(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TField field) throws org.apache.thrift.TException {
    _Fields setField = _Fields.findByThriftId(field.id);
    if (setField != null) {
      switch (setField) {
        case AGENT_REGISTER_RESULT:
          if (field.type == AGENT_REGISTER_RESULT_FIELD_DESC.type) {
            AgentRegisterResult agentRegisterResult;
            agentRegisterResult = new AgentRegisterResult();
            agentRegisterResult.read(iprot);
            return agentRegisterResult;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        case SCHEDULER_REGISTER_RESULT:
          if (field.type == SCHEDULER_REGISTER_RESULT_FIELD_DESC.type) {
            SchedulerRegisterResult schedulerRegisterResult;
            schedulerRegisterResult = new SchedulerRegisterResult();
            schedulerRegisterResult.read(iprot);
            return schedulerRegisterResult;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        case SCHEDULER_VERIFY_TASKS_RESULT:
          if (field.type == SCHEDULER_VERIFY_TASKS_RESULT_FIELD_DESC.type) {
            SchedulerVerifyTasksResult schedulerVerifyTasksResult;
            schedulerVerifyTasksResult = new SchedulerVerifyTasksResult();
            schedulerVerifyTasksResult.read(iprot);
            return schedulerVerifyTasksResult;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        case SCHEDULER_GET_TASK_PORTS_RESULT:
          if (field.type == SCHEDULER_GET_TASK_PORTS_RESULT_FIELD_DESC.type) {
            SchedulerGetTaskPortsResult schedulerGetTaskPortsResult;
            schedulerGetTaskPortsResult = new SchedulerGetTaskPortsResult();
            schedulerGetTaskPortsResult.read(iprot);
            return schedulerGetTaskPortsResult;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        case SCHEDULER_GET_STATE_RESULT:
          if (field.type == SCHEDULER_GET_STATE_RESULT_FIELD_DESC.type) {
            SchedulerGetStateResult schedulerGetStateResult;
            schedulerGetStateResult = new SchedulerGetStateResult();
            schedulerGetStateResult.read(iprot);
            return schedulerGetStateResult;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        default:
          throw new IllegalStateException("setField wasn't null, but didn't match any of the case statements!");
      }
    } else {
      org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
      return null;
    }
  }

  @Override
  protected void standardSchemeWriteValue(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    switch (setField_) {
      case AGENT_REGISTER_RESULT:
        AgentRegisterResult agentRegisterResult = (AgentRegisterResult)value_;
        agentRegisterResult.write(oprot);
        return;
      case SCHEDULER_REGISTER_RESULT:
        SchedulerRegisterResult schedulerRegisterResult = (SchedulerRegisterResult)value_;
        schedulerRegisterResult.write(oprot);
        return;
      case SCHEDULER_VERIFY_TASKS_RESULT:
        SchedulerVerifyTasksResult schedulerVerifyTasksResult = (SchedulerVerifyTasksResult)value_;
        schedulerVerifyTasksResult.write(oprot);
        return;
      case SCHEDULER_GET_TASK_PORTS_RESULT:
        SchedulerGetTaskPortsResult schedulerGetTaskPortsResult = (SchedulerGetTaskPortsResult)value_;
        schedulerGetTaskPortsResult.write(oprot);
        return;
      case SCHEDULER_GET_STATE_RESULT:
        SchedulerGetStateResult schedulerGetStateResult = (SchedulerGetStateResult)value_;
        schedulerGetStateResult.write(oprot);
        return;
      default:
        throw new IllegalStateException("Cannot write union with unknown field " + setField_);
    }
  }

  @Override
  protected Object tupleSchemeReadValue(org.apache.thrift.protocol.TProtocol iprot, short fieldID) throws org.apache.thrift.TException {
    _Fields setField = _Fields.findByThriftId(fieldID);
    if (setField != null) {
      switch (setField) {
        case AGENT_REGISTER_RESULT:
          AgentRegisterResult agentRegisterResult;
          agentRegisterResult = new AgentRegisterResult();
          agentRegisterResult.read(iprot);
          return agentRegisterResult;
        case SCHEDULER_REGISTER_RESULT:
          SchedulerRegisterResult schedulerRegisterResult;
          schedulerRegisterResult = new SchedulerRegisterResult();
          schedulerRegisterResult.read(iprot);
          return schedulerRegisterResult;
        case SCHEDULER_VERIFY_TASKS_RESULT:
          SchedulerVerifyTasksResult schedulerVerifyTasksResult;
          schedulerVerifyTasksResult = new SchedulerVerifyTasksResult();
          schedulerVerifyTasksResult.read(iprot);
          return schedulerVerifyTasksResult;
        case SCHEDULER_GET_TASK_PORTS_RESULT:
          SchedulerGetTaskPortsResult schedulerGetTaskPortsResult;
          schedulerGetTaskPortsResult = new SchedulerGetTaskPortsResult();
          schedulerGetTaskPortsResult.read(iprot);
          return schedulerGetTaskPortsResult;
        case SCHEDULER_GET_STATE_RESULT:
          SchedulerGetStateResult schedulerGetStateResult;
          schedulerGetStateResult = new SchedulerGetStateResult();
          schedulerGetStateResult.read(iprot);
          return schedulerGetStateResult;
        default:
          throw new IllegalStateException("setField wasn't null, but didn't match any of the case statements!");
      }
    } else {
      throw new TProtocolException("Couldn't find a field with field id " + fieldID);
    }
  }

  @Override
  protected void tupleSchemeWriteValue(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    switch (setField_) {
      case AGENT_REGISTER_RESULT:
        AgentRegisterResult agentRegisterResult = (AgentRegisterResult)value_;
        agentRegisterResult.write(oprot);
        return;
      case SCHEDULER_REGISTER_RESULT:
        SchedulerRegisterResult schedulerRegisterResult = (SchedulerRegisterResult)value_;
        schedulerRegisterResult.write(oprot);
        return;
      case SCHEDULER_VERIFY_TASKS_RESULT:
        SchedulerVerifyTasksResult schedulerVerifyTasksResult = (SchedulerVerifyTasksResult)value_;
        schedulerVerifyTasksResult.write(oprot);
        return;
      case SCHEDULER_GET_TASK_PORTS_RESULT:
        SchedulerGetTaskPortsResult schedulerGetTaskPortsResult = (SchedulerGetTaskPortsResult)value_;
        schedulerGetTaskPortsResult.write(oprot);
        return;
      case SCHEDULER_GET_STATE_RESULT:
        SchedulerGetStateResult schedulerGetStateResult = (SchedulerGetStateResult)value_;
        schedulerGetStateResult.write(oprot);
        return;
      default:
        throw new IllegalStateException("Cannot write union with unknown field " + setField_);
    }
  }

  @Override
  protected org.apache.thrift.protocol.TField getFieldDesc(_Fields setField) {
    switch (setField) {
      case AGENT_REGISTER_RESULT:
        return AGENT_REGISTER_RESULT_FIELD_DESC;
      case SCHEDULER_REGISTER_RESULT:
        return SCHEDULER_REGISTER_RESULT_FIELD_DESC;
      case SCHEDULER_VERIFY_TASKS_RESULT:
        return SCHEDULER_VERIFY_TASKS_RESULT_FIELD_DESC;
      case SCHEDULER_GET_TASK_PORTS_RESULT:
        return SCHEDULER_GET_TASK_PORTS_RESULT_FIELD_DESC;
      case SCHEDULER_GET_STATE_RESULT:
        return SCHEDULER_GET_STATE_RESULT_FIELD_DESC;
      default:
        throw new IllegalArgumentException("Unknown field id " + setField);
    }
  }

  @Override
  protected org.apache.thrift.protocol.TStruct getStructDesc() {
    return STRUCT_DESC;
  }

  @Override
  protected _Fields enumForId(short id) {
    return _Fields.findByThriftIdOrThrow(id);
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }


  public AgentRegisterResult getAgentRegisterResult() {
    if (getSetField() == _Fields.AGENT_REGISTER_RESULT) {
      return (AgentRegisterResult)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'agentRegisterResult' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setAgentRegisterResult(AgentRegisterResult value) {
    if (value == null) throw new NullPointerException();
    setField_ = _Fields.AGENT_REGISTER_RESULT;
    value_ = value;
  }

  public SchedulerRegisterResult getSchedulerRegisterResult() {
    if (getSetField() == _Fields.SCHEDULER_REGISTER_RESULT) {
      return (SchedulerRegisterResult)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'schedulerRegisterResult' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setSchedulerRegisterResult(SchedulerRegisterResult value) {
    if (value == null) throw new NullPointerException();
    setField_ = _Fields.SCHEDULER_REGISTER_RESULT;
    value_ = value;
  }

  public SchedulerVerifyTasksResult getSchedulerVerifyTasksResult() {
    if (getSetField() == _Fields.SCHEDULER_VERIFY_TASKS_RESULT) {
      return (SchedulerVerifyTasksResult)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'schedulerVerifyTasksResult' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setSchedulerVerifyTasksResult(SchedulerVerifyTasksResult value) {
    if (value == null) throw new NullPointerException();
    setField_ = _Fields.SCHEDULER_VERIFY_TASKS_RESULT;
    value_ = value;
  }

  public SchedulerGetTaskPortsResult getSchedulerGetTaskPortsResult() {
    if (getSetField() == _Fields.SCHEDULER_GET_TASK_PORTS_RESULT) {
      return (SchedulerGetTaskPortsResult)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'schedulerGetTaskPortsResult' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setSchedulerGetTaskPortsResult(SchedulerGetTaskPortsResult value) {
    if (value == null) throw new NullPointerException();
    setField_ = _Fields.SCHEDULER_GET_TASK_PORTS_RESULT;
    value_ = value;
  }

  public SchedulerGetStateResult getSchedulerGetStateResult() {
    if (getSetField() == _Fields.SCHEDULER_GET_STATE_RESULT) {
      return (SchedulerGetStateResult)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'schedulerGetStateResult' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setSchedulerGetStateResult(SchedulerGetStateResult value) {
    if (value == null) throw new NullPointerException();
    setField_ = _Fields.SCHEDULER_GET_STATE_RESULT;
    value_ = value;
  }

  public boolean isSetAgentRegisterResult() {
    return setField_ == _Fields.AGENT_REGISTER_RESULT;
  }


  public boolean isSetSchedulerRegisterResult() {
    return setField_ == _Fields.SCHEDULER_REGISTER_RESULT;
  }


  public boolean isSetSchedulerVerifyTasksResult() {
    return setField_ == _Fields.SCHEDULER_VERIFY_TASKS_RESULT;
  }


  public boolean isSetSchedulerGetTaskPortsResult() {
    return setField_ == _Fields.SCHEDULER_GET_TASK_PORTS_RESULT;
  }


  public boolean isSetSchedulerGetStateResult() {
    return setField_ == _Fields.SCHEDULER_GET_STATE_RESULT;
  }


  public boolean equals(Object other) {
    if (other instanceof Result) {
      return equals((Result)other);
    } else {
      return false;
    }
  }

  public boolean equals(Result other) {
    return other != null && getSetField() == other.getSetField() && getFieldValue().equals(other.getFieldValue());
  }

  @Override
  public int compareTo(Result other) {
    int lastComparison = org.apache.thrift.TBaseHelper.compareTo(getSetField(), other.getSetField());
    if (lastComparison == 0) {
      return org.apache.thrift.TBaseHelper.compareTo(getFieldValue(), other.getFieldValue());
    }
    return lastComparison;
  }


  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();
    list.add(this.getClass().getName());
    org.apache.thrift.TFieldIdEnum setField = getSetField();
    if (setField != null) {
      list.add(setField.getThriftFieldId());
      Object value = getFieldValue();
      if (value instanceof org.apache.thrift.TEnum) {
        list.add(((org.apache.thrift.TEnum)getFieldValue()).getValue());
      } else {
        list.add(value);
      }
    }
    return list.hashCode();
  }
  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }


  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }


}
