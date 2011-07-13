import sys

def main ():
	if len(sys.argv) < 4:
		print "Requires 3 arguments: Web Logic home directory, domains directory, and domain name"
		return

	wlsDirectory = sys.argv[1]
	domainsDirectory = sys.argv[2]
	domainName = sys.argv[3]

	port = 8080
	address = ''
	username = 'weblogic'
	password = 'testing123'
	templatePath = wlsDirectory + '/common/templates/domains/wls.jar'
	domainPath = domainsDirectory + '/' + domainName

	readTemplate(templatePath)

	set('Name', domainName)

	cd('/Security/%s/User/%s' % (domainName, username))
	set('UserPassword', password)

	cd('/Servers/AdminServer')
	set('ListenAddress', address)
	set('ListenPort', port)

	writeDomain(domainPath)

	closeTemplate()


sys.exit(main())
