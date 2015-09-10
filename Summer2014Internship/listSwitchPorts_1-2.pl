#!/usr/bin/perl -w
#
# listSitchPorts_1.2
#
# Modification history:
#
#     Date     Who  Comments
# Unknown      jrr  Initial Version
# 28-Mar-2009  jrr  V0.03
# 19-May-2010  jrr  V0.05 - Use MSSQL Arp table.
# 05-Jun-2010  jrr  V0.06 - Use .ndbpwd file to get credentials
# 01-Dec-2011  jrr  V0.07 - back to MySQL Arp table
# 06-Dec-2011  jrr  V0.08 - If port count is 0 put within body.
# 03-Jan-2012  jrr  V0.09 - If PortStatus is 8 then is disabled.
# 31-Aug-2012  jrr  V0.10 - Use new means to determine PortName from LineID port.
# 17-Apr-2014  jrr  V0.11 - Better algorithm to determine port from LineID.


## use lib "/home/jroman/cisco/NDB-1.2";
use lib "/home/noc/lib";
use CGI;
use CGI qw(:param);
use DBI;
use UNumber;
use Port;
use Constants;

# Perl trim function to remove whitespace from the start and end of the string
sub trim($) {
    my $string = shift;
    $string =~ s/^\s+//;
    $string =~ s/\s+$//;
    return $string;
}

sub portically {
  my $a_is_numeric = ($a =~ /^\d+$/);
  my $b_is_numeric = ($b =~ /^\d+$/);
  return $a <=> $b if $a_is_numeric and $b_is_numeric;

  my $SlashCountA = $a =~ tr/\//\//;  # number of slashes in $a
  my $SlashCountB = $b =~ tr/\//\//;  # number of slashes in $b
  return $a cmp $b if ($SlashCountA == 0) or ($SlashCountB == 0);

  my $dummy;
  my $modulea;
  my $porta;
  if ($SlashCountA == 1) {
    ($modulea, $porta) = split '/', $a;
  } else {
    ($modulea, $dummy, $porta) = split '/', $a;    # assumes exactly 2 slashes
  }
  my $moduleb;
  my $portb;
  if ($SlashCountB == 1) {
    ($moduleb, $portb) = split '/', $b;
  } else {
    ($moduleb, $dummy, $portb) = split '/', $b;    # assumes exactly 2 slashes
  }
  my $modulea_is_numeric = ($modulea =~ /^\d+$/);
  my $moduleb_is_numeric = ($moduleb =~ /^\d+$/);
  return $modulea <=> $moduleb if $modulea_is_numeric and $moduleb_is_numeric and ($modulea != $moduleb);

  
  return -1 if length($modulea) lt length($moduleb);
  return 1 if length($modulea) gt length($moduleb);
  return -1 if $modulea lt $moduleb;
  return 1 if $modulea gt $moduleb;
  # the modules are equal, so test the ports
  #return -1 if length($porta) lt length($portb);
  #return 1 if length($porta) gt length($portb);
  my $porta_is_numeric = ($a =~ /^\d+$/);
  my $portb_is_numeric = ($b =~ /^\d+$/);
  return $porta <=> $portb if $porta_is_numeric and $portb_is_numeric;
  return -1 if length($porta) lt length($portb);
  return 1 if length($porta) gt length($portb);

  return -1 if $porta lt $portb;
  return 1 if $porta gt $portb;
  return 0;
}


#my $name = 'device';
my $name = 'myInput';
my $switch;
$switch = param($name);
## $switch = 'mssc-cam06a-z';

my $pass = '';
my $user = '';

$ndbfile = '/var/www/.ndbpwd';
open (PWD, $ndbfile) or die "Can't open .ndbpwd file: $!\n";
while (<PWD>) {
    chomp;
    if ($_ !~ /^#/ ) {
        ($a, $b) = split(/=/);
        if ($a eq 'PASS') {
            $pass = $b;
        }
        if ($a eq 'USER') {
            $user = $b;
        }
    }
}
close PWD;

my $LIDdbh = DBI->connect("DBI:Sybase:server=MSCNS7", $user, $pass, {PrintError => 0});
unless ($LIDdbh) {
    die "Unable for connect to server $DBI::errstr";
}

my $devmodel = '';

$cmd = "USE MSCNS\nSELECT NodeInventory.Node, NodeInventory.NodeInventoryId, NodeInventory.BuildingCode, NodeInventory.TR_name,
      NodeIPs.IPAddress, NodeInventory.ModelNumber, NodeInventory.DeviceModel FROM NodeInventory
      INNER JOIN NodeIPs ON NodeInventory.Node = NodeIPs.Node where NodeInventory.Node ='$switch'";
## print "$cmd\n";
$LIDsth = $LIDdbh->prepare($cmd);
$LIDsth->execute() or die "Unable to execute query to select from NodeIPs: $LIDdbh->errstr\n";
while(@dat = $LIDsth->fetchrow) {
   $node = lc $dat[0];
   $devmodel = $dat[6];
}

$Portcount = 0;			# Switch port count

my $sth = $LIDdbh->prepare("USE MSCNS\n select * from UNumber where Node ='$switch'");

if ($sth->execute) {
    while(@dat = $sth->fetchrow) {
        my $UNumber = $dat[0];
        my $UN = new UNumber $dat[0];
        $UN->{BillingID} = $dat[1];
        $UN->{PatronID} = $dat[2];
        $UN->{BuildingCode} = $dat[3];
        $UN->{Room} = $dat[4];
        $UN->{Device} = $dat[5];
        $UN->{ConnectionTypeCode} = $dat[6];
        $UN->{MediaTypeCode} = $dat[7];
        $UN->{CableRoute} = $dat[8];
        $UN->{Node} = lc $dat[9];
        # Fix stupid data entry errors
        if ( defined ($dat[10]) && $dat[10] =~ /(\d+)\/(\d+)/ ) {
           if ( $1 eq '0' ) {
                $dat[10] = $2;
            }
        }
        if ( defined ($dat[10]) && $dat[10] =~ /(\d+)-(\d+)/ ) {
            $dat[10] = "$1/$2";
        }
        $dat[10] = trim $dat[10];
        $UN->{Port} = lc $dat[10];
        my $port = $UN->{Port};
        $UN->{EthernetAddress} = $dat[11];
        $UN->{IPAddress} = $dat[12];
        $UN->{DECNetAddress} = $dat[13];
        $UN->{AppleTalkZone} = $dat[14];
        $UN->{LineCharge} = $dat[15];
        $UN->{MiscellaneousInfo} = $dat[16];
        $UN->{UNumberStatus} = $dat[17];
        $UN->{ServiceTypeCode} = $dat[18];
        $UN->{JobNumber} = $dat[19];
        $UN->{ActivityTypeCode} = $dat[20];
        $UN->{CompletionDate} = $dat[21];
        $UN->{PerformedBy} = $dat[22];
        $UN->{ActivateDate} = $dat[23];
        $UN->{DeactivateDate} = $dat[24];
        $UN->{LastActivityDate} = $dat[25];
        $UN->{LastActivityDoneBy} = $dat[26];
        $UN->{LineTypeCode} = $dat[27];
        $UN->{Version} = $dat[28];
        $UN->{ContactFirstName} = $dat[29];
        $UN->{ContactLastName} = $dat[30];
        $UN->{ContactPhoneNumber} = $dat[31];
        $UN->{OldUNumber} = $dat[32];
        $UN->{dhcp} = $dat[33];
        $UN->{subnet} = $dat[34];
        $UN->{isdhcp} = $dat[35];
        $UN->{secure} = $dat[36];
        if ( !defined($dat[37]) ) { $dat[37] = 0; }
        $UN->{wireless} = $dat[37];
        $UN->{APline} = $dat[38];
        $UN->{VoIP} = $dat[39];
        $UN->{AltBillingID} = $dat[40];

        $nodeport = Constants::guessPort ($node, $devmodel, $port);

        if ( $nodeport eq '') {
            print "NO guess for $node devmodel\n";
        }
        $UN->{PortGuess} = $nodeport;

        $UN->{Key} = $nodeport;
        $lineidH{$nodeport} = $UN;
    }
}

my $NDBdsn = 'DBI:mysql:ndb:localhost';
my $NDB_user_name = 'mscits';
my $NDB_password = 'GreenSideUp&';
my $NDBdbh;
my $PortID;

$NDBdbh = DBI->connect($NDBdsn, $NDB_user_name, $NDB_password) or die "Can't connect to NDB Database";

$sth = $NDBdbh->prepare("SELECT PortId, PortSwitch, PortName, PortStatus, PortIdlesince, PortMac, PortNeighbor, PortSpeed, PortUNumber, PortLineID FROM Port where PortSwitch='$switch'") or die "Unable to execute query: $NDBdbh->errstr\n";
$sth->execute();
while (($PortID, $PortSwitch, $PortName, $PortStatus, $PortIdlesince, $PortMac, $PortNeighbor, $PortSpeed, $PortUNumber, $PortLineID) = $sth->fetchrow ) {
    $Portcount++;
    $Port = new Port;
    $PortSwitch = lc $PortSwitch;
    $Port->{PortSwitch} = lc $PortSwitch;
    $PortName = trim $PortName;
    $PortName = lc $PortName;
    $Port->{PortName} = lc $PortName;

    $Port->{PortMac} = trim($PortMac);
    if ( length($PortNeighbor) > 1) {
        (@flds) = split(/\./, $PortNeighbor);
        $PortNeighbor = $flds[0];
    }
    $Port->{PortNeighbor} = $PortNeighbor;
    $Port->{PortSpeed} = $PortSpeed;
    $Port->{PortStatus} = $PortStatus;
    $Port->{PortIdlesince} = $PortIdlesince;
    $Port->{PortUNumber} = $PortUNumber;
    $Port->{PortLineID} = $PortLineID;
    $switch_port = $PortName;
    $pH{$PortName} = $Port;
}

$sth->finish();


print "Content-type: text/html\n\n";
print "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n\n";
print "<html>\n\n";
print <<EOD1;
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>Network Database V0.08</title>

<style type="text/css">
/*margin and padding on body element
  can introduce errors in determining
  element position and are not recommended;
  we turn them off as a foundation for YUI
  CSS treatments. */
body {
        margin:0;
        padding:0;
}
</style>


<!-- Combo-handled YUI CSS files: --> 
<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/combo?2.8.2r1/build/datatable/assets/skins/sam/datatable.css"> 
<!-- Combo-handled YUI JS files: --> 
<script type="text/javascript" src="http://yui.yahooapis.com/combo?2.8.2r1/build/yahoo-dom-event/yahoo-dom-event.js&2.8.2r1/build/dragdrop/dragdrop-min.js&2.8.2r1/build/element/element-min.js&2.8.2r1/build/datasource/datasource-min.js&2.8.2r1/build/datatable/datatable-min.js"></script>

<!---
<link rel="stylesheet" type="text/css" href="../yui/build/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="../yui/build/datatable/assets/skins/sam/datatable.css" />
<script type="text/javascript" src="../yui/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="../yui/build/dragdrop/dragdrop-min.js"></script>
<script type="text/javascript" src="../yui/build/element/element-beta-min.js"></script>
<script type="text/javascript" src="../yui/build/datasource/datasource-min.js"></script>
<script type="text/javascript" src="../yui/build/datatable/datatable-min.js"></script>
-->


<!--begin custom header content for this example-->
<style type="text/css">
/* custom styles for this example */
.yui-skin-sam .yui-dt-liner { white-space:nowrap; } 
.test77 {font-size:77%;}
</style>

<!--end custom header content for this example-->

</head>

<body class=" yui-skin-sam">

<h1>Network Database V0.08</h1>

<div class="exampleIntro">
EOD1
print "<p>Data for switch $switch</p>\n";
if ($Portcount == 0) {
    print "<p>No ports found in Network DB for $switch</p>\n";
    ## exit;
}
print <<EOD2;
                        
</div>

<!--BEGIN SOURCE CODE FOR EXAMPLE =============================== -->

<div id="basic", class="test77"></div>

<script type="text/javascript">
YAHOO.example.Data = {
    ports: [
EOD2

$count = 0;
foreach $p ( sort portically keys %pH) {
    $port = $pH{$p};
    $switchport = $port->{PortSwitch} . "_" . $port->{PortName};
    # TODO -- discount connections to other switches -- if CDP is non-null (unless AP)
    if ($port->{PortStatus} eq '1') {
        $status = 'Up';
    } elsif ($port->{PortStatus} eq '2') {
        $status = 'Down';
    } elsif ($port->{PortStatus} eq '8') {
        $status = 'Disabled';
    } else {
        $status = 'Unknown';
    }
    $PortMac = $port->{PortMac};
    $PortNeighbor = $port->{PortNeighbor};
    $PortSpeed = $port->{PortSpeed};
    $ps = '100M';
    if ($PortSpeed == 1000000000 ) {
        $ps = '1G';
    } elsif ($PortSpeed == 10000000 ) {
        $ps = '10M';
    }
    if ( length($PortMac) == 0 ) {
       $countMacs = 0;
       $mac ='';
    } else {
       (@macs) = split (/ /, $PortMac);
       $countMacs = @macs;
    }
    $ip = '';
    if ( $countMacs > 1 ) { 
        $mac = "several"; 
    } elsif ( $countMacs == 1 ) {
        $mac = $PortMac;
        $query = "SELECT ArpIp FROM Arp where ArpMac='$PortMac' order by ArpDate DESC";
        $sth = $NDBdbh->prepare($query) or die "Unable to execute query: $NDBdbh->errstr\n";
        ## $sth = $LIDdbh->prepare($query) or die "Unable to execute query: $NDBdbh->errstr\n";
        if ($sth->execute() ) {
            ($xip) = $sth->fetchrow;
            $ip = $xip;
        }
        $sth->finish();
    }
    $idlesince = $port->{PortIdlesince};
    if ($idlesince == 0 ) {
        $timestamp = "Never";
    } else {
        my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) = localtime($idlesince);
        $timestamp = sprintf "%04d/%02d/%02d", $year+1900, $mon+1, $mday;
    }
    if (! defined ( $lineidH{$p} ) ) {
        $unumber = 'No LineID';
        $un_node = '';
        $un_port = '';
        $un_ctc = '';
        $un_status = '';
        $un_billingid = '';
    } else {
        $unumber = $lineidH{$p}->{UNumber};
        $un_node = $lineidH{$p}->{Node};
        $un_port = $lineidH{$p}->{Port};
        $un_ctc = $lineidH{$p}->{ConnectionTypeCode};
        $un_status = $lineidH{$p}->{UNumberStatus};
        $un_billingid = $lineidH{$p}->{BillingID};
    }
    $count++;
    if ( $count > 1 ) {print ","; } 
    ## $ip = '';
    print "{LineID:\"$unumber\", PortName: \"$port->{PortName}\", PortStatus: \"$status\", Idlesince: \"$timestamp\", PortMac: \"$mac\", IP: \"$ip\", Type: \"$un_ctc\", Status: \"$un_status\", Neighbor: \"$PortNeighbor\", BillingID:\"$un_billingid\"}\n";
}
print  <<EOD3;
    ]
};
</script>

<script type="text/javascript">
YAHOO.util.Event.addListener(window, "load", function() {
    YAHOO.example.Basic = function() {
        var myColumnDefs = [
            {key:"LineID", sortable:true, resizeable:true},
            {key:"PortName", sortable:true, resizeable:true},
            {key:"PortStatus", sortable:true, resizeable:true},
            {key:"Idlesince", sortable:true, resizeable:true},
            {key:"PortMac", sortable:true, resizeable:true},
            {key:"IP", sortable:true, resizeable:true},
            {key:"Type", sortable:true, resizeable:true},
            {key:"Status", sortable:true, resizeable:true},
            {key:"Neighbor", sortable:true, resizeable:true},
            {key:"BillingID", sortable:true, resizeable:true}
        ];

        var myDataSource = new YAHOO.util.DataSource(YAHOO.example.Data.ports);
        myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
        myDataSource.responseSchema = {
            fields: ["LineID","PortName","PortStatus","Idlesince","PortMac","IP","Type","Status","Neighbor","BillingID"]
        };

        var myDataTable = new YAHOO.widget.DataTable("basic",
                myColumnDefs, myDataSource, {caption:"Switchport information"});
                
        return {
            oDS: myDataSource,
            oDT: myDataTable
        };
    }();
});
</script>
</body>
</html>
EOD3
exit(0);
