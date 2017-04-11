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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2017-03-14")
public class Scheduler implements org.apache.thrift.TBase<Scheduler, Scheduler._Fields>, java.io.Serializable, Cloneable, Comparable<Scheduler> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Scheduler");

  private static final org.apache.thrift.protocol.TField INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("info", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField HEALTH_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("healthInfo", org.apache.thrift.protocol.TType.STRUCT, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new SchedulerStandardSchemeFactory());
    schemes.put(TupleScheme.class, new SchedulerTupleSchemeFactory());
  }

  public MachineDescriptor info; // required
  public HealthCheckConfiguration healthInfo; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    INFO((short)1, "info"),
    HEALTH_INFO((short)2, "healthInfo");

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
        case 1: // INFO
          return INFO;
        case 2: // HEALTH_INFO
          return HEALTH_INFO;
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

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.INFO, new org.apache.thrift.meta_data.FieldMetaData("info", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, MachineDescriptor.class)));
    tmpMap.put(_Fields.HEALTH_INFO, new org.apache.thrift.meta_data.FieldMetaData("healthInfo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, HealthCheckConfiguration.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Scheduler.class, metaDataMap);
  }

  public Scheduler() {
  }

  public Scheduler(
    MachineDescriptor info,
    HealthCheckConfiguration healthInfo)
  {
    this();
    this.info = info;
    this.healthInfo = healthInfo;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Scheduler(Scheduler other) {
    if (other.isSetInfo()) {
      this.info = new MachineDescriptor(other.info);
    }
    if (other.isSetHealthInfo()) {
      this.healthInfo = new HealthCheckConfiguration(other.healthInfo);
    }
  }

  public Scheduler deepCopy() {
    return new Scheduler(this);
  }

  @Override
  public void clear() {
    this.info = null;
    this.healthInfo = null;
  }

  public MachineDescriptor getInfo() {
    return this.info;
  }

  public Scheduler setInfo(MachineDescriptor info) {
    this.info = info;
    return this;
  }

  public void unsetInfo() {
    this.info = null;
  }

  /** Returns true if field info is set (has been assigned a value) and false otherwise */
  public boolean isSetInfo() {
    return this.info != null;
  }

  public void setInfoIsSet(boolean value) {
    if (!value) {
      this.info = null;
    }
  }

  public HealthCheckConfiguration getHealthInfo() {
    return this.healthInfo;
  }

  public Scheduler setHealthInfo(HealthCheckConfiguration healthInfo) {
    this.healthInfo = healthInfo;
    return this;
  }

  public void unsetHealthInfo() {
    this.healthInfo = null;
  }

  /** Returns true if field healthInfo is set (has been assigned a value) and false otherwise */
  public boolean isSetHealthInfo() {
    return this.healthInfo != null;
  }

  public void setHealthInfoIsSet(boolean value) {
    if (!value) {
      this.healthInfo = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case INFO:
      if (value == null) {
        unsetInfo();
      } else {
        setInfo((MachineDescriptor)value);
      }
      break;

    case HEALTH_INFO:
      if (value == null) {
        unsetHealthInfo();
      } else {
        setHealthInfo((HealthCheckConfiguration)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case INFO:
      return getInfo();

    case HEALTH_INFO:
      return getHealthInfo();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case INFO:
      return isSetInfo();
    case HEALTH_INFO:
      return isSetHealthInfo();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Scheduler)
      return this.equals((Scheduler)that);
    return false;
  }

  public boolean equals(Scheduler that) {
    if (that == null)
      return false;

    boolean this_present_info = true && this.isSetInfo();
    boolean that_present_info = true && that.isSetInfo();
    if (this_present_info || that_present_info) {
      if (!(this_present_info && that_present_info))
        return false;
      if (!this.info.equals(that.info))
        return false;
    }

    boolean this_present_healthInfo = true && this.isSetHealthInfo();
    boolean that_present_healthInfo = true && that.isSetHealthInfo();
    if (this_present_healthInfo || that_present_healthInfo) {
      if (!(this_present_healthInfo && that_present_healthInfo))
        return false;
      if (!this.healthInfo.equals(that.healthInfo))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_info = true && (isSetInfo());
    list.add(present_info);
    if (present_info)
      list.add(info);

    boolean present_healthInfo = true && (isSetHealthInfo());
    list.add(present_healthInfo);
    if (present_healthInfo)
      list.add(healthInfo);

    return list.hashCode();
  }

  @Override
  public int compareTo(Scheduler other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetInfo()).compareTo(other.isSetInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetInfo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.info, other.info);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetHealthInfo()).compareTo(other.isSetHealthInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHealthInfo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.healthInfo, other.healthInfo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Scheduler(");
    boolean first = true;

    sb.append("info:");
    if (this.info == null) {
      sb.append("null");
    } else {
      sb.append(this.info);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("healthInfo:");
    if (this.healthInfo == null) {
      sb.append("null");
    } else {
      sb.append(this.healthInfo);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (info != null) {
      info.validate();
    }
    if (healthInfo != null) {
      healthInfo.validate();
    }
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

  private static class SchedulerStandardSchemeFactory implements SchemeFactory {
    public SchedulerStandardScheme getScheme() {
      return new SchedulerStandardScheme();
    }
  }

  private static class SchedulerStandardScheme extends StandardScheme<Scheduler> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Scheduler struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // INFO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.info = new MachineDescriptor();
              struct.info.read(iprot);
              struct.setInfoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // HEALTH_INFO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.healthInfo = new HealthCheckConfiguration();
              struct.healthInfo.read(iprot);
              struct.setHealthInfoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Scheduler struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.info != null) {
        oprot.writeFieldBegin(INFO_FIELD_DESC);
        struct.info.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.healthInfo != null) {
        oprot.writeFieldBegin(HEALTH_INFO_FIELD_DESC);
        struct.healthInfo.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SchedulerTupleSchemeFactory implements SchemeFactory {
    public SchedulerTupleScheme getScheme() {
      return new SchedulerTupleScheme();
    }
  }

  private static class SchedulerTupleScheme extends TupleScheme<Scheduler> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Scheduler struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetInfo()) {
        optionals.set(0);
      }
      if (struct.isSetHealthInfo()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetInfo()) {
        struct.info.write(oprot);
      }
      if (struct.isSetHealthInfo()) {
        struct.healthInfo.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Scheduler struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.info = new MachineDescriptor();
        struct.info.read(iprot);
        struct.setInfoIsSet(true);
      }
      if (incoming.get(1)) {
        struct.healthInfo = new HealthCheckConfiguration();
        struct.healthInfo.read(iprot);
        struct.setHealthInfoIsSet(true);
      }
    }
  }

}

