package Port;

use strict;


sub new {
  my $type = shift;

  my $this = {};
  $this->{AuxiliaryVlanNbr} = 0;
  $this->{CdpCachePlatform} = '';
  $this->{DaysInactive} = 0;
  $this->{Duplex} = 'unknown';
  $this->{EtherChannel} = 0;    # reference to an EtherChannel object, if this port is etherchanneled
  $this->{PortIdlesince} = -1;
  $this->{PortIfNbr} = 0;
  $this->{IsConnectedToIpPhone} = 0;
  $this->{IsTrunking} = 0;
  $this->{Label} = '';
  $this->{PortMac} = {};           # a hash holding one or more MACs
  $this->{PortName} = '';
  $this->{PortSwitch} = '';
  $this->{PortLineID} = '';
  $this->{PortNeighbor} = '';
  $this->{PortSpeed} = '';
  $this->{PortId} = 0;
  $this->{PortType} = '';
  $this->{PortStatus} = 'Unknown';
  $this->{Unused} = 0;        # boolean: has the port been IdleSince for UnusadAfter days?
  $this->{PortVlan} = 0;
  $this->{PortIp} = '';
  $this->{PortID} = '';
  return bless $this;
}

sub AddMac ($$) {
  my $this = shift;
  my $Mac = shift;
  $this->{PortMac}{$Mac}++;
}

1;
