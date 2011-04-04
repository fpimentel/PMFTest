/*
	Simple OpenID Plugin
	http://code.google.com/p/openid-selector/
	
	This code is licensed under the New BSD License.
*/

var providers_large = {
	google : {
		name : 'Google',
		url : 'https://www.google.com/accounts/o8/id'
	},
	yahoo : {
		name : 'Yahoo',
		url : 'http://me.yahoo.com/'
	},
	aol : {
		name : 'AOL',
		label : 'Introduzca su usuario de AOL.',
		url : 'http://openid.aol.com/{username}'
	},
	myopenid : {
		name : 'MyOpenID',
		label : 'Introduzca su usuario de MyOpenID.',
		url : 'http://{username}.myopenid.com/'
	},
	openid : {
		name : 'OpenID',
		label : 'Introduzca su usuario de OpenID.',
		url : null
	}
};

var providers_small = {
	livejournal : {
		name : 'LiveJournal',
		label : 'Introduzca su usuario de Livejournal.',
		url : 'http://{username}.livejournal.com/'
	},
	/* flickr: {
		name: 'Flickr',        
		label: 'Introduzca su usuario de Flickr.',
		url: 'http://flickr.com/{username}/'
	}, */
	/* technorati: {
		name: 'Technorati',
		label: 'Introduzca su usuario de Technorati.',
		url: 'http://technorati.com/people/technorati/{username}/'
	}, */
	wordpress : {
		name : 'Wordpress',
		label : 'Introduzca su usuario de Wordpress.com.',
		url : 'http://{username}.wordpress.com/'
	},
	blogger : {
		name : 'Blogger',
		label : 'Su cuenta de Blogger',
		url : 'http://{username}.blogspot.com/'
	},
	verisign : {
		name : 'Verisign',
		label : 'Su usuario de Verisign',
		url : 'http://{username}.pip.verisignlabs.com/'
	},
	/* vidoop: {
		name: 'Vidoop',
		label: 'Your Vidoop username',
		url: 'http://{username}.myvidoop.com/'
	}, */
	/* launchpad: {
		name: 'Launchpad',
		label: 'Your Launchpad username',
		url: 'https://launchpad.net/~{username}'
	}, */
	claimid : {
		name : 'ClaimID',
		label : 'Su usuario de ClaimID',
		url : 'http://claimid.com/{username}'
	},
	clickpass : {
		name : 'ClickPass',
		label : 'Introduzca su usuario de ClickPass',
		url : 'http://clickpass.com/public/{username}'
	},
	google_profile : {
		name : 'Google Profile',
		label : 'Introduzca su usuario de Google Profile',
		url : 'http://www.google.com/profiles/{username}'
	}
};

openid.locale = 'en';
openid.sprite = 'en'; // reused in german& japan localization
openid.demo_text = 'In client demo mode. Normally would have submitted OpenID:';
openid.signin_text = 'Iniciar Sesi&oacute;n';
openid.image_title = 'Iniciar sesi&oacute;n con {provider}';
