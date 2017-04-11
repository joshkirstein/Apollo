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
public class Response implements org.apache.thrift.TBase<Response, Response._Fields>, java.io.Serializable, Cloneable, Comparable<Response> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Response");

  private static final org.apache.thrift.protocol.TField RESPONSE_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("responseCode", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField RESULT_FIELD_DESC = new org.apache.thrift.protocol.TField("result", org.apache.thrift.protocol.TType.STRUCT, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ResponseStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ResponseTupleSchemeFactory());
  }

  /**
   * 
   * @see ResponseCode
   */
  public ResponseCode responseCode; // required
  public Result result; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 
     * @see ResponseCode
     */
    RESPONSE_CODE((short)1, "responseCode"),
    RESULT((short)2, "result");

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
        case 1: // RESPONSE_CODE
          return RESPONSE_CODE;
        case 2: // RESULT
          return RESULT;
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
  private static final _Fields optionals[] = {_Fields.RESULT};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.RESPONSE_CODE, new org.apache.thrift.meta_data.FieldMetaData("responseCode", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, ResponseCode.class)));
    tmpMap.put(_Fields.RESULT, new org.apache.thrift.meta_data.FieldMetaData("result", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Result.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Response.class, metaDataMap);
  }

  public Response() {
  }

  public Response(
    ResponseCode responseCode)
  {
    this();
    this.responseCode = responseCode;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Response(Response other) {
    if (other.isSetResponseCode()) {
      this.responseCode = other.responseCode;
    }
    if (other.isSetResult()) {
      this.result = new Result(other.result);
    }
  }

  public Response deepCopy() {
    return new Response(this);
  }

  @Override
  public void clear() {
    this.responseCode = null;
    this.result = null;
  }

  /**
   * 
   * @see ResponseCode
   */
  public ResponseCode getResponseCode() {
    return this.responseCode;
  }

  /**
   * 
   * @see ResponseCode
   */
  public Response setResponseCode(ResponseCode responseCode) {
    this.responseCode = responseCode;
    return this;
  }

  public void unsetResponseCode() {
    this.responseCode = null;
  }

  /** Returns true if field responseCode is set (has been assigned a value) and false otherwise */
  public boolean isSetResponseCode() {
    return this.responseCode != null;
  }

  public void setResponseCodeIsSet(boolean value) {
    if (!value) {
      this.responseCode = null;
    }
  }

  public Result getResult() {
    return this.result;
  }

  public Response setResult(Result result) {
    this.result = result;
    return this;
  }

  public void unsetResult() {
    this.result = null;
  }

  /** Returns true if field result is set (has been assigned a value) and false otherwise */
  public boolean isSetResult() {
    return this.result != null;
  }

  public void setResultIsSet(boolean value) {
    if (!value) {
      this.result = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case RESPONSE_CODE:
      if (value == null) {
        unsetResponseCode();
      } else {
        setResponseCode((ResponseCode)value);
      }
      break;

    case RESULT:
      if (value == null) {
        unsetResult();
      } else {
        setResult((Result)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESPONSE_CODE:
      return getResponseCode();

    case RESULT:
      return getResult();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case RESPONSE_CODE:
      return isSetResponseCode();
    case RESULT:
      return isSetResult();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Response)
      return this.equals((Response)that);
    return false;
  }

  public boolean equals(Response that) {
    if (that == null)
      return false;

    boolean this_present_responseCode = true && this.isSetResponseCode();
    boolean that_present_responseCode = true && that.isSetResponseCode();
    if (this_present_responseCode || that_present_responseCode) {
      if (!(this_present_responseCode && that_present_responseCode))
        return false;
      if (!this.responseCode.equals(that.responseCode))
        return false;
    }

    boolean this_present_result = true && this.isSetResult();
    boolean that_present_result = true && that.isSetResult();
    if (this_present_result || that_present_result) {
      if (!(this_present_result && that_present_result))
        return false;
      if (!this.result.equals(that.result))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_responseCode = true && (isSetResponseCode());
    list.add(present_responseCode);
    if (present_responseCode)
      list.add(responseCode.getValue());

    boolean present_result = true && (isSetResult());
    list.add(present_result);
    if (present_result)
      list.add(result);

    return list.hashCode();
  }

  @Override
  public int compareTo(Response other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetResponseCode()).compareTo(other.isSetResponseCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResponseCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.responseCode, other.responseCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetResult()).compareTo(other.isSetResult());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResult()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.result, other.result);
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
    StringBuilder sb = new StringBuilder("Response(");
    boolean first = true;

    sb.append("responseCode:");
    if (this.responseCode == null) {
      sb.append("null");
    } else {
      sb.append(this.responseCode);
    }
    first = false;
    if (isSetResult()) {
      if (!first) sb.append(", ");
      sb.append("result:");
      if (this.result == null) {
        sb.append("null");
      } else {
        sb.append(this.result);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
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

  private static class ResponseStandardSchemeFactory implements SchemeFactory {
    public ResponseStandardScheme getScheme() {
      return new ResponseStandardScheme();
    }
  }

  private static class ResponseStandardScheme extends StandardScheme<Response> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Response struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // RESPONSE_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.responseCode = com.apollo.thriftgen.ResponseCode.findByValue(iprot.readI32());
              struct.setResponseCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // RESULT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.result = new Result();
              struct.result.read(iprot);
              struct.setResultIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Response struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.responseCode != null) {
        oprot.writeFieldBegin(RESPONSE_CODE_FIELD_DESC);
        oprot.writeI32(struct.responseCode.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.result != null) {
        if (struct.isSetResult()) {
          oprot.writeFieldBegin(RESULT_FIELD_DESC);
          struct.result.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ResponseTupleSchemeFactory implements SchemeFactory {
    public ResponseTupleScheme getScheme() {
      return new ResponseTupleScheme();
    }
  }

  private static class ResponseTupleScheme extends TupleScheme<Response> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Response struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetResponseCode()) {
        optionals.set(0);
      }
      if (struct.isSetResult()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetResponseCode()) {
        oprot.writeI32(struct.responseCode.getValue());
      }
      if (struct.isSetResult()) {
        struct.result.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Response struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.responseCode = com.apollo.thriftgen.ResponseCode.findByValue(iprot.readI32());
        struct.setResponseCodeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.result = new Result();
        struct.result.read(iprot);
        struct.setResultIsSet(true);
      }
    }
  }

}

