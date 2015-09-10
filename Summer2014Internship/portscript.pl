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
# 29-Jul-2014  smm  V0.12 - Rewritten for ServiceNow query


## use lib "/home/jroman/cisco/NDB-1.2";
use lib "/home/noc/lib";
use CGI;
use CGI qw(:param);
use DBI;
use UNumber;
use Port_temp;
use Constants;

# Perl trim function to remove whitespace from the start and end of the string
sub trim($) {
    my $string = shift;
    $string =~ s/^\s+//;
    $string =~ s/\s+$//;
    return $string;
}


my $name = 'device';
my $name = 'myInput';
my $switch;
my $holder;
$switch = param($name);
$holder = $switch;
#$switch = '00d68f5389a31100090da23bf5f0e43a';


$Portcount = 0;			# Switch port count

my $NDBdsn = 'DBI:mysql:NetworkCMDB_Test:localhost';
my $NDB_user_name = 'msns';
my $NDB_password = '#WashUBears#';
my $NDBdbh;

$NDBdbh = DBI->connect($NDBdsn, $NDB_user_name, $NDB_password) or die "Can't connect to NDB Database";
$sth = $NDBdbh->prepare("SELECT sys_id FROM device where u_name='$switch'") or die "Unable to execute query: $NDBdbh->errstr\n";
$sth->execute();
while(($sys_id) = $sth->fetchrow ) {
	$switch = lc $sys_id;
}
$sth = $NDBdbh->prepare("SELECT sys_id, u_name, u_speed, u_status, u_mac, u_neighbor, u_idle_since FROM ports where u_device_id='$switch'") or die "Unable to execute query: $NDBdbh->errstr\n";
$sth->execute();
while (( $sys_id, $u_name, $u_speed, $u_status, $u_mac, $u_neighbor, $u_idle_since) = $sth->fetchrow ) {
    $Portcount++;
    $Port->{PortID} = $sys_id; 
    $Port = new Port;
    $PortName = trim $u_name;
    @ipMac = split(/\//, $u_mac);
    $PortMac = lc $ipMac[0];    
    $PortIp = lc $ipMac[1];
    $Port->{PortMac} = lc $PortMac;
    $Port->{PortIdlesince} = lc $u_idle_since;
    $Port->{PortIp} = lc $PortIp;
    $Port->{PortSpeed} = $u_speed;
    $Port->{PortNeighbor} = $u_neighbor;
    $PortName = lc $u_name;
    $Port->{PortName} = lc $PortName;
    $Port->{PortStatus} = lc $u_status;
    $switch_port = $PortName;
    $pH{$PortName} = $Port;
}



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
print "<p>Data for switch $holder</p>\n";
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
foreach $p (keys %pH) {
    $port = $pH{$p};
    $id = $port->{PortID};
	if ($id ne '') {
		$query = "SELECT u_unumber, u_active, u_connectiontype, u_billingaccount FROM linesid where u_port='$id'";
		$sth = $NDBdbh->prepare($query) or die "Unable to execute query: $NDBdbh->errstr\n";
		$sth->execute();
		while (( $u_unumber, $u_active, $u_connectiontype, $u_billingaccount) = $sth->fetchrow ) {
			$lineid = $u_unumber;
			$active = $u_active;
			$typecode = $u_connectiontype;
			$bill = $u_billingaccount;
		}
	}
	if ($typecode ne '') {
		$query = "SELECT u_connection_type_code FROM connectiontype where sys_id='$typecode'";
		$sth = $NDBdbh->prepare($query) or die "Unable to execute query: $NDBdbh->errstr\n";
		$sth->execute();
		while (( $u_connection_type_code) = $sth->fetchrow ) {
			$type = $u_connectiontype_code;
		}
	}

	$speed = $port->{PortSpeed};
    $name = $port->{PortName};
    $mac = $port->{PortMac};
    $ip = $port->{PortIp};
    if ($port->{PortIdlesince} eq '1900-01-01') {
    	$idle = 'Never';
    } else {
    	$idle = $port->{PortIdlesince};
    } 
    $neighbor = $port->{PortNeighbor};
    if ($port->{PortStatus} eq '1') {
        $status = 'Up';
    } elsif ($port->{PortStatus} eq '2') {
        $status = 'Down';
    } elsif ($port->{PortStatus} eq '8') {
        $status = 'Disabled';
    } else {
        $status = 'Unknown';
    }
    if ($lineid eq ''){
		$lineid = "No Line Id";
	}
    $count++;
    if ( $count > 1 ) {print ","; } 
    print "{LineID: \"$lineid\", PortName: \"$name\", PortSpeed: \"$speed\", PortStatus: \"$status\", PortMac: \"$mac\", Active: \"$active\", Type: \"$type\", IP: \"$ip\", Neighbor: \"$neighbor\", Idlesince: \"$idle\", BillingID: \"$bill\"}\n";
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
            {key:"PortSpeed", sortable:true, resizeable:true},
            {key:"PortStatus", sortable:true, resizable:true},
            {key:"PortMac", sortable:true, resizable:true},
            {key:"Active", sortable:true, resizable:true},
            {key:"Type", sortable:true, resizable:true},
            {key:"IP", sortable:true, resizable:true},
        	{key:"Neighbor", sortable:true, resizable:true},
        	{key:"Idlesince", sortable:true, resizable:true},
        	{key:"BillingID", sortable:true, resizable:true}                      
        ];

        var myDataSource = new YAHOO.util.DataSource(YAHOO.example.Data.ports);
        myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
        myDataSource.responseSchema = {
            fields: ["LineID","PortName","PortSpeed","PortStatus","PortMac","Active","Type","IP","Neighbor","Idlesince","BillingID"]
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
