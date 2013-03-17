postToAjax = (editor) -> 
    code = editor.getValue()
    highlightedCode = $('div.ace_text-layer').html()
    $.post('/interpret', $.param({line : code}), (result) ->
        $('#prev-content').append('<code class="prettyprint lang-scala">&gt; ' + code + '</code> <br />')
        $('#prev-content').append('<code class="prettyprint lang-scala">' +
        						  result +
        						  '</code><br /><br />')
    )
    editor.setValue("")
    $('body').load(prettyPrint())
    
$('document').ready(() ->
    editor = ace.edit("editor")
    editor.setTheme("ace/theme/chrome")
    editor.getSession().setMode("ace/mode/scala")
    editor.setValue("")
    document.getElementById('editor').style.fontSize='20px';
    editor.commands.addCommand({
        name: 'myCommand',
        bindKey: {win: 'Ctrl-Return',  mac: 'Command-Return'},
        exec: (editor) -> postToAjax(editor)
    })
    editor.getSession().on('change', (e) ->
    	if(editor.session.getLength() < 10) 
    		$('div.ace_gutter').width(0)
    		$('div.ace_scrollbar').width(0)
    		length = editor.session.getLength() * 20
    		$('div#editor').height(length)
    		editor.resize()
    	else 
    		$('div.ace_gutter').width(40)
    		$('div.ace_scrollbar').width(20)
    )
    $('div.ace_gutter').width(0)
    $('div.ace_scrollbar').width(0)
	$('#code').submit((e) ->
		postToAjax(editor)
		$('body').load(prettyPrint())
		e.preventDefault()
	)
)