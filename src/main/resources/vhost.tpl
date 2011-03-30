<VirtualHost *:$port$>
	ServerAdmin webmaster@localhost
	ServerName $hostname$

	DocumentRoot $documentRoot$

	<Directory />
		Options FollowSymLinks
		AllowOverride None
	</Directory>

	#directoryConfig#
	<Directory $basedir$>
		$directoryConfig$
	</Directory>
	#/directoryConfig#

	#extraConfig#
	$extraConfig$
	#/extraConfig#

	ErrorLog ${APACHE_LOG_DIR}/error.log
	LogLevel warn
	CustomLog ${APACHE_LOG_DIR}/access.log combined
</VirtualHost>