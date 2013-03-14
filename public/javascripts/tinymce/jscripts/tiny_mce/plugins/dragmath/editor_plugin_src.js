/**
 * editor_plugin_src.js
 *
 * Math
 */

(function() {
	// Load plugin specific language pack
	//tinymce.PluginManager.requireLangPack('mathematik');

	tinymce.create('tinymce.plugins.DragMath', {
		/**
		 * Initializes the plugin, this will be executed after the plugin has been created.
		 * This call is done before the editor instance has finished it's initialization so use the onInit event
		 * of the editor instance to intercept that event.
		 *
		 * @param {tinymce.Editor} ed Editor instance that the plugin is initialized in.
		 * @param {string} url Absolute URL to where the plugin is located.
		 */
		init : function(ed, url) {

		    this.editor = ed;
		    var t = this;

			// Register commands
			ed.addCommand('mceDragInsert', function(){
			    ed.windowManager.open({
                file : url + '/dragMathPopup.html',
                width : 500,
                height : 450,
                inline : 1
             }, {
                plugin_url : url
             });
         });

         ed.addButton('dragmath', {
            title : 'Drag Math',
            command : 'mceDragInsert',
            image : url + '/img/sqrt.png'
         });
		},

		/**
		 * Returns information about the plugin as a name/value array.
		 * The current keys are longname, author, authorurl, infourl and version.
		 *
		 * @return {Object} Name/value array containing information about the plugin.
		 */
		getInfo : function() {
			return {
				longname : 'DragMath: Integration of the DragMath Equation Editor Applet in TinyMCE',
				author : 'Tyler Darnell',
				authorurl : '',
				infourl : '',
				version : "1.0"
			};
		}
	});

	// Register plugin
	tinymce.PluginManager.add('dragmath', tinymce.plugins.DragMath);
})();
