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
public class TaskDescriptor implements org.apache.thrift.TBase<TaskDescriptor, TaskDescriptor._Fields>, java.io.Serializable, Cloneable, Comparable<TaskDescriptor> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TaskDescriptor");

  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField URL_LOCATER_FIELD_DESC = new org.apache.thrift.protocol.TField("urlLocater", org.apache.thrift.protocol.TType.STRUCT, (short)3);
  private static final org.apache.thrift.protocol.TField RESOURCES_FIELD_DESC = new org.apache.thrift.protocol.TField("resources", org.apache.thrift.protocol.TType.SET, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TaskDescriptorStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TaskDescriptorTupleSchemeFactory());
  }

  public String name; // required
  public String id; // required
  public FetcherURI urlLocater; // required
  public Set<Resource> resources; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    NAME((short)1, "name"),
    ID((short)2, "id"),
    URL_LOCATER((short)3, "urlLocater"),
    RESOURCES((short)4, "resources");

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
        case 1: // NAME
          return NAME;
        case 2: // ID
          return ID;
        case 3: // URL_LOCATER
          return URL_LOCATER;
        case 4: // RESOURCES
          return RESOURCES;
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
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.URL_LOCATER, new org.apache.thrift.meta_data.FieldMetaData("urlLocater", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, FetcherURI.class)));
    tmpMap.put(_Fields.RESOURCES, new org.apache.thrift.meta_data.FieldMetaData("resources", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.SetMetaData(org.apache.thrift.protocol.TType.SET, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Resource.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TaskDescriptor.class, metaDataMap);
  }

  public TaskDescriptor() {
  }

  public TaskDescriptor(
    String name,
    String id,
    FetcherURI urlLocater,
    Set<Resource> resources)
  {
    this();
    this.name = name;
    this.id = id;
    this.urlLocater = urlLocater;
    this.resources = resources;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TaskDescriptor(TaskDescriptor other) {
    if (other.isSetName()) {
      this.name = other.name;
    }
    if (other.isSetId()) {
      this.id = other.id;
    }
    if (other.isSetUrlLocater()) {
      this.urlLocater = new FetcherURI(other.urlLocater);
    }
    if (other.isSetResources()) {
      Set<Resource> __this__resources = new HashSet<Resource>(other.resources.size());
      for (Resource other_element : other.resources) {
        __this__resources.add(new Resource(other_element));
      }
      this.resources = __this__resources;
    }
  }

  public TaskDescriptor deepCopy() {
    return new TaskDescriptor(this);
  }

  @Override
  public void clear() {
    this.name = null;
    this.id = null;
    this.urlLocater = null;
    this.resources = null;
  }

  public String getName() {
    return this.name;
  }

  public TaskDescriptor setName(String name) {
    this.name = name;
    return this;
  }

  public void unsetName() {
    this.name = null;
  }

  /** Returns true if field name is set (has been assigned a value) and false otherwise */
  public boolean isSetName() {
    return this.name != null;
  }

  public void setNameIsSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  public String getId() {
    return this.id;
  }

  public TaskDescriptor setId(String id) {
    this.id = id;
    return this;
  }

  public void unsetId() {
    this.id = null;
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return this.id != null;
  }

  public void setIdIsSet(boolean value) {
    if (!value) {
      this.id = null;
    }
  }

  public FetcherURI getUrlLocater() {
    return this.urlLocater;
  }

  public TaskDescriptor setUrlLocater(FetcherURI urlLocater) {
    this.urlLocater = urlLocater;
    return this;
  }

  public void unsetUrlLocater() {
    this.urlLocater = null;
  }

  /** Returns true if field urlLocater is set (has been assigned a value) and false otherwise */
  public boolean isSetUrlLocater() {
    return this.urlLocater != null;
  }

  public void setUrlLocaterIsSet(boolean value) {
    if (!value) {
      this.urlLocater = null;
    }
  }

  public int getResourcesSize() {
    return (this.resources == null) ? 0 : this.resources.size();
  }

  public java.util.Iterator<Resource> getResourcesIterator() {
    return (this.resources == null) ? null : this.resources.iterator();
  }

  public void addToResources(Resource elem) {
    if (this.resources == null) {
      this.resources = new HashSet<Resource>();
    }
    this.resources.add(elem);
  }

  public Set<Resource> getResources() {
    return this.resources;
  }

  public TaskDescriptor setResources(Set<Resource> resources) {
    this.resources = resources;
    return this;
  }

  public void unsetResources() {
    this.resources = null;
  }

  /** Returns true if field resources is set (has been assigned a value) and false otherwise */
  public boolean isSetResources() {
    return this.resources != null;
  }

  public void setResourcesIsSet(boolean value) {
    if (!value) {
      this.resources = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((String)value);
      }
      break;

    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((String)value);
      }
      break;

    case URL_LOCATER:
      if (value == null) {
        unsetUrlLocater();
      } else {
        setUrlLocater((FetcherURI)value);
      }
      break;

    case RESOURCES:
      if (value == null) {
        unsetResources();
      } else {
        setResources((Set<Resource>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case NAME:
      return getName();

    case ID:
      return getId();

    case URL_LOCATER:
      return getUrlLocater();

    case RESOURCES:
      return getResources();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case NAME:
      return isSetName();
    case ID:
      return isSetId();
    case URL_LOCATER:
      return isSetUrlLocater();
    case RESOURCES:
      return isSetResources();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TaskDescriptor)
      return this.equals((TaskDescriptor)that);
    return false;
  }

  public boolean equals(TaskDescriptor that) {
    if (that == null)
      return false;

    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }

    boolean this_present_id = true && this.isSetId();
    boolean that_present_id = true && that.isSetId();
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (!this.id.equals(that.id))
        return false;
    }

    boolean this_present_urlLocater = true && this.isSetUrlLocater();
    boolean that_present_urlLocater = true && that.isSetUrlLocater();
    if (this_present_urlLocater || that_present_urlLocater) {
      if (!(this_present_urlLocater && that_present_urlLocater))
        return false;
      if (!this.urlLocater.equals(that.urlLocater))
        return false;
    }

    boolean this_present_resources = true && this.isSetResources();
    boolean that_present_resources = true && that.isSetResources();
    if (this_present_resources || that_present_resources) {
      if (!(this_present_resources && that_present_resources))
        return false;
      if (!this.resources.equals(that.resources))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_name = true && (isSetName());
    list.add(present_name);
    if (present_name)
      list.add(name);

    boolean present_id = true && (isSetId());
    list.add(present_id);
    if (present_id)
      list.add(id);

    boolean present_urlLocater = true && (isSetUrlLocater());
    list.add(present_urlLocater);
    if (present_urlLocater)
      list.add(urlLocater);

    boolean present_resources = true && (isSetResources());
    list.add(present_resources);
    if (present_resources)
      list.add(resources);

    return list.hashCode();
  }

  @Override
  public int compareTo(TaskDescriptor other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetName()).compareTo(other.isSetName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.name, other.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUrlLocater()).compareTo(other.isSetUrlLocater());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUrlLocater()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.urlLocater, other.urlLocater);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetResources()).compareTo(other.isSetResources());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResources()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.resources, other.resources);
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
    StringBuilder sb = new StringBuilder("TaskDescriptor(");
    boolean first = true;

    sb.append("name:");
    if (this.name == null) {
      sb.append("null");
    } else {
      sb.append(this.name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("id:");
    if (this.id == null) {
      sb.append("null");
    } else {
      sb.append(this.id);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("urlLocater:");
    if (this.urlLocater == null) {
      sb.append("null");
    } else {
      sb.append(this.urlLocater);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("resources:");
    if (this.resources == null) {
      sb.append("null");
    } else {
      sb.append(this.resources);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (urlLocater != null) {
      urlLocater.validate();
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

  private static class TaskDescriptorStandardSchemeFactory implements SchemeFactory {
    public TaskDescriptorStandardScheme getScheme() {
      return new TaskDescriptorStandardScheme();
    }
  }

  private static class TaskDescriptorStandardScheme extends StandardScheme<TaskDescriptor> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TaskDescriptor struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.setNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.id = iprot.readString();
              struct.setIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // URL_LOCATER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.urlLocater = new FetcherURI();
              struct.urlLocater.read(iprot);
              struct.setUrlLocaterIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // RESOURCES
            if (schemeField.type == org.apache.thrift.protocol.TType.SET) {
              {
                org.apache.thrift.protocol.TSet _set0 = iprot.readSetBegin();
                struct.resources = new HashSet<Resource>(2*_set0.size);
                Resource _elem1;
                for (int _i2 = 0; _i2 < _set0.size; ++_i2)
                {
                  _elem1 = new Resource();
                  _elem1.read(iprot);
                  struct.resources.add(_elem1);
                }
                iprot.readSetEnd();
              }
              struct.setResourcesIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TaskDescriptor struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.name != null) {
        oprot.writeFieldBegin(NAME_FIELD_DESC);
        oprot.writeString(struct.name);
        oprot.writeFieldEnd();
      }
      if (struct.id != null) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeString(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.urlLocater != null) {
        oprot.writeFieldBegin(URL_LOCATER_FIELD_DESC);
        struct.urlLocater.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.resources != null) {
        oprot.writeFieldBegin(RESOURCES_FIELD_DESC);
        {
          oprot.writeSetBegin(new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRUCT, struct.resources.size()));
          for (Resource _iter3 : struct.resources)
          {
            _iter3.write(oprot);
          }
          oprot.writeSetEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TaskDescriptorTupleSchemeFactory implements SchemeFactory {
    public TaskDescriptorTupleScheme getScheme() {
      return new TaskDescriptorTupleScheme();
    }
  }

  private static class TaskDescriptorTupleScheme extends TupleScheme<TaskDescriptor> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TaskDescriptor struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetName()) {
        optionals.set(0);
      }
      if (struct.isSetId()) {
        optionals.set(1);
      }
      if (struct.isSetUrlLocater()) {
        optionals.set(2);
      }
      if (struct.isSetResources()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetName()) {
        oprot.writeString(struct.name);
      }
      if (struct.isSetId()) {
        oprot.writeString(struct.id);
      }
      if (struct.isSetUrlLocater()) {
        struct.urlLocater.write(oprot);
      }
      if (struct.isSetResources()) {
        {
          oprot.writeI32(struct.resources.size());
          for (Resource _iter4 : struct.resources)
          {
            _iter4.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TaskDescriptor struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.name = iprot.readString();
        struct.setNameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.id = iprot.readString();
        struct.setIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.urlLocater = new FetcherURI();
        struct.urlLocater.read(iprot);
        struct.setUrlLocaterIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TSet _set5 = new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.resources = new HashSet<Resource>(2*_set5.size);
          Resource _elem6;
          for (int _i7 = 0; _i7 < _set5.size; ++_i7)
          {
            _elem6 = new Resource();
            _elem6.read(iprot);
            struct.resources.add(_elem6);
          }
        }
        struct.setResourcesIsSet(true);
      }
    }
  }

}

