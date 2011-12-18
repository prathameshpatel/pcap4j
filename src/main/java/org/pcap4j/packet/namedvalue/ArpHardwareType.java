/*_##########################################################################
  _##
  _##  Copyright (C) 2011  Kaito Yamada
  _##
  _##########################################################################
*/

package org.pcap4j.packet.namedvalue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public
final class ArpHardwareType
extends NamedNumber<Short> implements Comparable<Short> {

  // http://www.iana.org/assignments/arp-parameters/arp-parameters.xml#hardware-type-rules
  public static final ArpHardwareType ETHERNET
    = new ArpHardwareType((short)1, "Ethernet(10Mb)");
  public static final ArpHardwareType EXPERIMENTAL_ETHERNET
    = new ArpHardwareType((short)2, "ExperimentalEthernet(3Mb)");
  public static final ArpHardwareType FRAME_RELAY
    = new ArpHardwareType((short)15, "FrameRelay");
  public static final ArpHardwareType IPSEC_TUNNEL
    = new ArpHardwareType((short)31, "IPsec tunnel");

  private static Map<Short, ArpHardwareType> registry
    = new HashMap<Short, ArpHardwareType>();

  static {
    for (Field field: ArpHardwareType.class.getFields()) {
      if (field.getType().isAssignableFrom(ArpHardwareType.class)) {
        try {
          ArpHardwareType typeCode = (ArpHardwareType)field.get(null);
          registry.put(typeCode.value(), typeCode);
        } catch (IllegalArgumentException e) {
          throw new AssertionError(e);
        } catch (IllegalAccessException e) {
          throw new AssertionError(e);
        } catch (NullPointerException e) {
          continue;
        }
      }
    }
  }

  private ArpHardwareType(Short value, String name) {
    super(value, name);
  }

  public static ArpHardwareType getInstance(Short value) {
    if (registry.containsKey(value)) {
      return registry.get(value);
    }
    else {
      return new ArpHardwareType(value, "unknown");
    }
  }

  @Override
  public String valueAsString() {
    return String.valueOf(value() & 0xFFFF);
  }

  public int compareTo(Short o) {
    return value().compareTo(o);
  }

}