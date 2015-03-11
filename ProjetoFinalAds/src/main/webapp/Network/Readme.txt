************************************************************************
SOFTWARE LICENSE AGREEMENT

YOU, THE END OF USER, SHALL USE THIS SOFTWARE ("THE SOFTWARE")
ACCORDING TO THE TERMS OF THIS AGREEMENT.  BY LOADING THE SOFTWARE
INTO ANY COMPUTER, YOU ARE AGREEING TO BE BOUND BY THESE TERMS.
IF YOU DO NOT ACCEPT THE TERMS OF THIS AGREEMENT, YOU MAY NOT LOAD
SOFTWARE INTO ANY COMPUTER.

Grant of License
The Software is licensed for use by you for the equipment packaged with
the Software or designated by its supplier ("the equipment"). You are
permitted to use the Software on any computer which permits electronic
access to the equipment. You are not permitted to rent or lease the
Software or to transfer your rights under this license to a third party.

Acceptance
You shall be deemed to have accepted the terms of this Agreement by
loading the Software into any computer.

Duration
This license is effective until terminated. The license will terminate
where you fail to comply with the terms of this Agreement. Upon
termination, you agree to destroy all copies of the Software and its
documentation. 

Ownership of Software 
You own, if any, only the magnetic media on which the Software has been
delivered. It is an express condition of this Agreement that the title
and ownership of the Software shall be retained by its owners.

Copying
The Software and its documentation are the subject of Copyright.
You may not make any copies nor cause others to make copies of the
Software except such copies as are necessary for licensed use and one
copy for operational security. Any such copies are subject to the
conditions of this Agreement.
You may not modify, adapt, merge, translate, reverse engineer, decompile,
disassemble or create derivative works based on the whole or any part of
the Software or its associated documentation.
You may be held legally responsible for any copyright infringement,
unauthorized transfer, reproduction or use of the Software or its
documentation.

Limitation of Warranty
The media, if any, not the Software contained therein, is warranted to the
original purchaser against defects in material and workmanship for a
period of one year from the date of original purchase. Defective media
will be replaced when it is returned to your supplier, postage prepaid,
along with a copy of the purchase receipt.
THESE RIGHTS ARE YOUR SOLE AND EXCLUSIVE REMEDY, WHETHER IN TORT, CONTRACT,
OR OTHERWISE. IN NO EVENT SHALL SUPPLIER, OWNER OF THE SOFTWARE OR ITS
LICENSORS BE LIABLE FOR DAMAGES (INCLUDING ANY FOR LOSS OF INFORMATION,
PROFITS, SAVINGS OR BUSINESS INTERRUPTION OR OTHER DIRECT, INDIRECT,
INCIDENTAL, CONSEQUENTIAL OR SPECIAL DAMAGES) ARISING OUT OF THE SOFTWARE
OR THE USE THEREOF, OR THE INABILITY TO USE THE SOFTWARE.

Exporting Law
You acknowledge that the export of the Software is governed by the export
control laws of the United States and other countries. You agree to comply
with all such export control laws.

Governing Law and Jurisdiction
If any provision or portion of this Agreement shall be found to be illegal,
invalid or unenforceable, the remaining provisions shall remain in full
force and effect. This Agreement shall be governed by the laws of California,
USA and is subject to the jurisdiction of the Federal District courts for the
Central District of California by a judge and not a jury.

************************************************************************

                        =============================
                           WIA Driver Ver: 1.0.84.2
                        =============================
Updated: April, 2014

Welcome to WIA Driver.
This document file contains information about 32-bit and 64 bit(x64) WIA
scanner driver.
Please refer to this document when you use the driver.


Contents
  OPERATIONAL ENVIRONMENT
  HOW TO INSTALL
  INSTRUCTIONS FOR USE

--------------------------------------------
- OPERATIONAL ENVIRONMENT
--------------------------------------------

 * From the OS below is available to use.
     Windows Vista(after Service Pack 1)
     Windows Server 2008
     Windows 7
     Windows Server 2008 R2
     Windows 8
     Windows Server 2012
     Windows 8.1
     Windows Server 2012 R2

   Please check our website to see a list of the latest supported operating systems.

 * From the OS below's Remote Desktop Services (Terminal Services) is available to use.
  (Note: WIA driver installed in client PC is unavailable via RemoteFX.)
     Windows Server 2008
     Windows Server 2008 R2
     Windows Server 2012
     Windows Server 2012 R2

 * This driver is not available to install under OS for IA-64( Intel
  Architecture 64).

--------------------------------------------
- HOW TO INSTALL
--------------------------------------------

  *Administrator authority is required for installation.

  1. Open [Control Panel] from [Start] menu, select [Scanners and Cameras] from
     [Hardware and Sound].
     Run [Add Device] and start [Scanner and Camera Installation Wizard].
     appendix1:
       Please add [User Experience], when [Scanners and Cameras] does not appear in
       Windows Server 2008.
       Reference: http://support.microsoft.com/kb/947036/
     appendix2:
       When [Scanner and Camera]menu is not shown in [Control Panel],
       it would able to be shown by either the method below. 
       (Method 1)
         1.Run [Help and Support] from [Start] menu.(run Windows Help)
         2.Click [Options]-[Browse Help].
         3.Click [Hardware, devices, and drivers].
         4.Click [Scanners and scanning].
         5.Click [Working with the scanner and Camera Installation Wizard].
         6.Click [To start the Scanner and Camera Installation Wizard].
         7.Click [Click to open Scanners and Cameras].
       (Method 2)
         1.From [Start] menu, select [All Programs]-[Accessories]-[Run].
         2.Type "control sticpl.cpl" and click [OK].
  2. Select [Next] when wizard starting dialog appears.
  3. Select [Have Disk] in Device Select dialog, then specify ini file which is
     contained in the driver directory.
  4. Select [Next] in Model Select dialog after selecting scanner model to use.
  5. Select [Next] in device name specify dialog after specifying an optional name.
     When using Twain compatible function, it is recommended to name within 25 byte.
  6. Select [Finish] when finishing wizard dialog shows. 

  * Firewall Settings
   It is necessary to connect to scanner through network for using scanner with
   the driver installed.
   For this reason, in Windows Firewall or other Firewall products,
   WIA(Windows Imaging Acquisition) service and applications using WIA service
   must be permitted the following connections between scanners.
          Protocol        Local port        Remote Port
            UDP             Any               161
            TCP             Any               Any

--------------------------------------------
- INSTRUCTIONS FOR USE
--------------------------------------------

 * At ADF scan,set the top edge of the original toward page feeding direction to display
   top/bottom of image as intended on computer.
   It might not be displayed correctly when scanning in other set direction.
   When scanning 2sides, set the top edge of original(original to be scanned Top to Top)
   toward paper feeding direction in order to display the front and back page correctly. 

 * Duplex scanning is available only under the application that supports
   continuous scanning. When using the application which does not support 
   continuous scanning, even if you select "Duplex", only one front page will
   be scanned.

 * Even if ADF duplex scanning is selected, only front side is previewed.

 * In some applications, driver error message may not be showed. (ex. Paint)

 * When the driver dialog is displayed, press [Close] button and close the
   driver before closing the application. 

 * According to the application using, and when setting different parameter to
   X resolution and Y resolution, sometimes the application may not work as
   usual.

 * When you  power on scanner after setting document on fladbed and closing
   the ADF, the autodetect function of the document does not work properly.
   Set the document after the scanner's power is on and the format finishes.

 * Preview image is scanned with the fixed value, therefore if X and
   Y-resolution have different values, preview may have different image from
   the scan image.

 * When you set different parameter to X resolution and Y resolution and then
   execute scanning, aspect ratio of scanned image may be displayed and printed
   sometimes different from original. It depends on application you use.

 * IC card certification is not able.

 * General User Authentication and User Code Authentication setting is necessary
   on [Authenticate] tab of property sheet which is shown when [Properties] is
   selected on [Scanners and Cameras].

 * When a user who does not have the Administrator authority executes,
   [Properties] of [Scanners and Cameras] can not be selected.
   Please use [Change] of Windows FAX and Scan or Windows Photo system UI.

 * When there are multiple scanners on the same network which can be connected
   using same driver, please specify the [IP address or host name] on
   [Network Connection] tab from [Properties].

 * In [Diagnose] from [Properties], does not always test to the scanner configured
   for the user to connect.
   If another user had used a scanner before executing [Diagnose], it may work
   on the scanner another user had configured.

 * It is not available to use the same driver at the same time after installing
   more than one.
   You must close the application once and release the device. 

 * The driver file is common between the models shown during the installation,
   however please select the proper model as much as possible.

 * When you use Twain compatible function, a problem might occur since the driver
   name is long.
   Please set the name of the device which you have to specify during installation
   under 25 bytes when you use this function.

 * When you scan using Twain compatible function, a line might appear on the left side
    of image. 

 * When you enable IPv6-only scanner and you enter a host name by 
   [Network Connection] tab, WIA driver may not find the scanner. 
   Please enable LLMNR by Web Image Monitor.

 * It might not be displayed correctly when scanning by application 
   which comply with the WIA 1.0 standard.
  (ex. Microsoft Office Document Imaging/Microsoft Office Document Scanning)
========================================================================

  Trademark Notices: Microsoft(R) and Windows(R) are registered trademarks of
  Microsoft Corporation in the United States and/or other countries.
