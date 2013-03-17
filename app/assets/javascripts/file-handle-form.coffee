compileWithAjax = (editor) -> 
    code = editor.getValue()
    fileId = $('#info').attr('fileId')
    $.post('/compile/' + fileId, $.param({line : code}), (result) -> 
    	$('#prev-content').html("")
    	$('#compiler-message').append(result)
    	)
	### interactions.setValue("") ###
	$('body').load(prettyPrint())

postToAjax = (editor) -> 
    code = editor.getValue()
    $.post('/interpret', $.param({line : code}), (result) ->
        $('#prev-content').append('<code class="prettyprint lang-scala">&gt; ' + code + '</code> <br />')
        $('#prev-content').append('<code class="prettyprint lang-scala">' +
        						  result +
        						  '</code><br /><br />')
    )
    editor.setValue("")
    $('body').load(prettyPrint())
    
$('document').ready(() ->
    ###
    Setting up the file editor.
    ###
    fileContent = $('#info').attr('fileContent')
    editor = ace.edit("editor")
    editor.setTheme("ace/theme/chrome")
    editor.getSession().setMode("ace/mode/scala")
    editor.setValue(fileContent)
    editor.clearSelection()
    document.getElementById('editor').style.fontSize='20px';
    editor.commands.addCommand({
        name: 'myCommand',
        bindKey: {win: 'Ctrl-Return',  mac: 'Command-Return'},
        exec: (editor) -> compileWithAjax(editor)
    })
    $('#compileButton').click(() -> compileWithAjax(editor))
    ###
    Setting up the interactions area.
    ###
    interactions = ace.edit("interactions")
    interactions.setTheme("ace/theme/chrome")
    interactions.getSession().setMode("ace/mode/scala")
    interactions.commands.addCommand({
        name: 'myCommand',
        bindKey: {win: 'Ctrl-Return',  mac: 'Command-Return'},
        exec: (editor) -> postToAjax(editor)
    })
    interactions.setValue("")
    document.getElementById('interactions').style.fontSize='20px';
    interactions.getSession().on('change', (e) ->
    	if(interactions.session.getLength() < 10) 
    		$('#interactions div.ace_gutter').width(0)
    		$('#interactions div.ace_scrollbar').width(0)
    		length = interactions.session.getLength() * 20
    		$('div#interactions').height(length)
    		interactions.resize()
    	else 
    		$('#interactions div.ace_gutter').width(40)
    		$('#interactions div.ace_scrollbar').width(20)
    )
    $('#interactions div.ace_gutter').width(0)
    $('#interactions div.ace_scrollbar').width(0)
	$('#code').submit((e) ->
		postToAjax(interactions)
		e.preventDefault()
	)
)