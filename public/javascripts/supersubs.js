  


<!DOCTYPE html>
<html>
  <head prefix="og: http://ogp.me/ns# fb: http://ogp.me/ns/fb# githubog: http://ogp.me/ns/fb/githubog#">
    <meta charset='utf-8'>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>superfish-reloaded/js/supersubs.js at master · bobbravo2/superfish-reloaded</title>
    <link rel="search" type="application/opensearchdescription+xml" href="/opensearch.xml" title="GitHub" />
    <link rel="fluid-icon" href="https://github.com/fluidicon.png" title="GitHub" />
    <link rel="apple-touch-icon-precomposed" sizes="57x57" href="apple-touch-icon-114.png" />
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="apple-touch-icon-114.png" />
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="apple-touch-icon-144.png" />
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="apple-touch-icon-144.png" />

    
    
    <link rel="icon" type="image/x-icon" href="/favicon.png" />

    <meta content="authenticity_token" name="csrf-param" />
<meta content="fySctfKktf+jlV1HjAzACRhHOLQCvJE4ZTOfGfqeodo=" name="csrf-token" />

    <link href="https://a248.e.akamai.net/assets.github.com/assets/github-39bf4cdee70b543b20dd99bf6c8274f5ba637ac6.css" media="screen" rel="stylesheet" type="text/css" />
    <link href="https://a248.e.akamai.net/assets.github.com/assets/github2-cf174ab4b5febe2ac7a9f95750f474ad1485e3a7.css" media="screen" rel="stylesheet" type="text/css" />
    
    


    <script src="https://a248.e.akamai.net/assets.github.com/assets/frameworks-cda884a5a58f7a231c16c075e16bc5c1509f192b.js" type="text/javascript"></script>
    
    <script defer="defer" src="https://a248.e.akamai.net/assets.github.com/assets/github-a82035096c9d23773a260bb5cdf1b418fef97230.js" type="text/javascript"></script>
    
    

      <link rel='permalink' href='/bobbravo2/superfish-reloaded/blob/aefb802bf0fd6911336e488e63803115e9358490/js/supersubs.js'>
    <meta property="og:title" content="superfish-reloaded"/>
    <meta property="og:type" content="githubog:gitrepository"/>
    <meta property="og:url" content="https://github.com/bobbravo2/superfish-reloaded"/>
    <meta property="og:image" content="https://a248.e.akamai.net/assets.github.com/images/gravatars/gravatar-140.png?1329275934"/>
    <meta property="og:site_name" content="GitHub"/>
    <meta property="og:description" content="Major refactoring of superfish. Contribute to superfish-reloaded development by creating an account on GitHub."/>

    <meta name="description" content="Major refactoring of superfish. Contribute to superfish-reloaded development by creating an account on GitHub." />

  <link href="https://github.com/bobbravo2/superfish-reloaded/commits/master.atom" rel="alternate" title="Recent Commits to superfish-reloaded:master" type="application/atom+xml" />

  </head>


  <body class="logged_in page-blob linux vis-public fork env-production ">
    <div id="wrapper">

    
    
    

      <div id="header" class="true clearfix">
        <div class="container clearfix">
          <a class="site-logo" href="https://github.com/">
            <!--[if IE]>
            <img alt="GitHub" class="github-logo" src="https://a248.e.akamai.net/assets.github.com/images/modules/header/logov7.png?1323882770" />
            <img alt="GitHub" class="github-logo-hover" src="https://a248.e.akamai.net/assets.github.com/images/modules/header/logov7-hover.png?1324325405" />
            <![endif]-->
            <img alt="GitHub" class="github-logo-4x" height="30" src="https://a248.e.akamai.net/assets.github.com/images/modules/header/logov7@4x.png?1337118069" />
            <img alt="GitHub" class="github-logo-4x-hover" height="30" src="https://a248.e.akamai.net/assets.github.com/images/modules/header/logov7@4x-hover.png?1337118069" />
          </a>



              
    <div class="topsearch  ">
        <form accept-charset="UTF-8" action="/search" id="top_search_form" method="get">
  <a href="/search" class="advanced-search tooltipped downwards" title="Advanced Search"><span class="mini-icon mini-icon-advanced-search"></span></a>
  <div class="search placeholder-field js-placeholder-field">
    <input type="text" class="search my_repos_autocompleter" id="global-search-field" name="q" results="5" spellcheck="false" autocomplete="off" data-autocomplete="my-repos-autocomplete" placeholder="Search&hellip;" data-hotkey="s" />
    <div id="my-repos-autocomplete" class="autocomplete-results">
      <ul class="js-navigation-container"></ul>
    </div>
    <input type="submit" value="Search" class="button">
    <span class="mini-icon mini-icon-search-input"></span>
  </div>
  <input type="hidden" name="type" value="Everything" />
  <input type="hidden" name="repo" value="" />
  <input type="hidden" name="langOverride" value="" />
  <input type="hidden" name="start_value" value="1" />
</form>

      <ul class="top-nav">
          <li class="explore"><a href="https://github.com/explore">Explore</a></li>
          <li><a href="https://gist.github.com">Gist</a></li>
          <li><a href="/blog">Blog</a></li>
        <li><a href="http://help.github.com">Help</a></li>
      </ul>
    </div>


            


  <div id="userbox">
    <div id="user">
      <a href="https://github.com/toddobryan"><img height="20" src="https://secure.gravatar.com/avatar/5c852227421efb23d4214f95adb09185?s=140&amp;d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-140.png" width="20" /></a>
      <a href="https://github.com/toddobryan" class="name">toddobryan</a>
    </div>
    <ul id="user-links">
      <li>
        <a href="/new" id="new_repo" class="tooltipped downwards" title="Create a New Repo"><span class="mini-icon mini-icon-create"></span></a>
      </li>
      <li>
        <a href="/inbox/notifications" id="notifications" class="tooltipped downwards" title="Notifications">
          <span class="mini-icon mini-icon-notifications"></span>
          <span class="unread_count">1</span>
        </a>
      </li>
      <li>
        <a href="/settings/profile" id="account_settings"
          class="tooltipped downwards"
          title="Account Settings ">
          <span class="mini-icon mini-icon-account-settings"></span>
        </a>
      </li>
      <li>
          <a href="/logout" data-method="post" id="logout" class="tooltipped downwards" title="Sign Out">
            <span class="mini-icon mini-icon-logout"></span>
          </a>
      </li>
    </ul>
  </div>



          
        </div>
      </div>

      

            <div class="site hfeed" itemscope itemtype="http://schema.org/WebPage">
      <div class="container hentry">
        <div class="pagehead repohead instapaper_ignore readability-menu">
        <div class="title-actions-bar">
          



              <ul class="pagehead-actions">



          <li class="js-toggler-container js-social-container watch-button-container ">
            <span class="watch-button"><a href="/bobbravo2/superfish-reloaded/toggle_watch" class="minibutton btn-watch js-toggler-target lighter" data-remote="true" data-method="post" rel="nofollow">Watch</a><a class="social-count js-social-count" href="/bobbravo2/superfish-reloaded/watchers">9</a></span>
            <span class="unwatch-button"><a href="/bobbravo2/superfish-reloaded/toggle_watch" class="minibutton btn-unwatch js-toggler-target lighter" data-remote="true" data-method="post" rel="nofollow">Unwatch</a><a class="social-count js-social-count" href="/bobbravo2/superfish-reloaded/watchers">9</a></span>
          </li>

              <li><a href="/bobbravo2/superfish-reloaded/fork" class="minibutton btn-fork js-toggler-target fork-button lighter" rel="nofollow" data-method="post">Fork</a><a href="/bobbravo2/superfish-reloaded/network" class="social-count">14</a>
              </li>

    </ul>

          <h1 itemscope itemtype="http://data-vocabulary.org/Breadcrumb" class="entry-title public">
            <span class="repo-label"><span>public</span></span>
            <span class="mega-icon mega-icon-repo-forked"></span>
            <span class="author vcard">
<a href="/bobbravo2" class="url fn" itemprop="url" rel="author">              <span itemprop="title">bobbravo2</span>
              </a></span> /
            <strong><a href="/bobbravo2/superfish-reloaded" class="js-current-repository">superfish-reloaded</a></strong>
              <span class="fork-flag">
                <span class="text">forked from <a href="/karevn/superfish">karevn/superfish</a></span>
              </span>
          </h1>
        </div>

          

  <ul class="tabs">
    <li><a href="/bobbravo2/superfish-reloaded" class="selected" highlight="repo_sourcerepo_downloadsrepo_commitsrepo_tagsrepo_branches">Code</a></li>
    <li><a href="/bobbravo2/superfish-reloaded/network" highlight="repo_network">Network</a>
    <li><a href="/bobbravo2/superfish-reloaded/pulls" highlight="repo_pulls">Pull Requests <span class='counter'>0</span></a></li>


      <li><a href="/bobbravo2/superfish-reloaded/wiki" highlight="repo_wiki">Wiki</a></li>

    <li><a href="/bobbravo2/superfish-reloaded/graphs" highlight="repo_graphsrepo_contributors">Graphs</a></li>


  </ul>
  
<div class="frame frame-center tree-finder" style="display:none"
      data-tree-list-url="/bobbravo2/superfish-reloaded/tree-list/aefb802bf0fd6911336e488e63803115e9358490"
      data-blob-url-prefix="/bobbravo2/superfish-reloaded/blob/aefb802bf0fd6911336e488e63803115e9358490"
    >

  <div class="breadcrumb">
    <span class="bold"><a href="/bobbravo2/superfish-reloaded">superfish-reloaded</a></span> /
    <input class="tree-finder-input js-navigation-enable" type="text" name="query" autocomplete="off" spellcheck="false">
  </div>

    <div class="octotip">
      <p>
        <a href="/bobbravo2/superfish-reloaded/dismiss-tree-finder-help" class="dismiss js-dismiss-tree-list-help" title="Hide this notice forever" rel="nofollow">Dismiss</a>
        <span class="bold">Octotip:</span> You've activated the <em>file finder</em>
        by pressing <span class="kbd">t</span> Start typing to filter the
        file list. Use <span class="kbd badmono">↑</span> and
        <span class="kbd badmono">↓</span> to navigate,
        <span class="kbd">enter</span> to view files.
      </p>
    </div>

  <table class="tree-browser" cellpadding="0" cellspacing="0">
    <tr class="js-header"><th>&nbsp;</th><th>name</th></tr>
    <tr class="js-no-results no-results" style="display: none">
      <th colspan="2">No matching files</th>
    </tr>
    <tbody class="js-results-list js-navigation-container">
    </tbody>
  </table>
</div>

<div id="jump-to-line" style="display:none">
  <h2>Jump to Line</h2>
  <form accept-charset="UTF-8">
    <input class="textfield" type="text">
    <div class="full-button">
      <button type="submit" class="classy">
        Go
      </button>
    </div>
  </form>
</div>


<div class="subnav-bar">

  <ul class="actions subnav">
    <li><a href="/bobbravo2/superfish-reloaded/tags" class="blank" highlight="repo_tags">Tags <span class="counter">0</span></a></li>
    <li><a href="/bobbravo2/superfish-reloaded/downloads" class="blank downloads-blank" highlight="repo_downloads">Downloads <span class="counter">0</span></a></li>
    
  </ul>

  <ul class="scope">
    <li class="switcher">

      <div class="context-menu-container js-menu-container js-context-menu">
        <a href="#"
           class="minibutton bigger switcher js-menu-target js-commitish-button btn-branch repo-tree"
           data-hotkey="w"
           data-master-branch="master"
           data-ref="master">
           <span><i>branch:</i> master</span>
        </a>

        <div class="context-pane commitish-context js-menu-content">
          <a href="javascript:;" class="close js-menu-close"><span class="mini-icon mini-icon-remove-close"></span></a>
          <div class="context-title">Switch branches/tags</div>
          <div class="context-body pane-selector commitish-selector js-navigation-container">
            <div class="filterbar">
              <input type="text" id="context-commitish-filter-field" class="js-navigation-enable" placeholder="Filter branches/tags" data-filterable />

              <ul class="tabs">
                <li><a href="#" data-filter="branches" class="selected">Branches</a></li>
                <li><a href="#" data-filter="tags">Tags</a></li>
              </ul>
            </div>

            <div class="js-filter-tab js-filter-branches" data-filterable-for="context-commitish-filter-field" data-filterable-type=substring>
              <div class="no-results js-not-filterable">Nothing to show</div>
                <div class="commitish-item branch-commitish selector-item js-navigation-item js-navigation-target selected">
                  <span class="mini-icon mini-icon-confirm"></span>
                  <h4>
                      <a href="/bobbravo2/superfish-reloaded/blob/master/js/supersubs.js" class="js-navigation-open" data-name="master" rel="nofollow">master</a>
                  </h4>
                </div>
            </div>

            <div class="js-filter-tab js-filter-tags" style="display:none" data-filterable-for="context-commitish-filter-field" data-filterable-type=substring>
              <div class="no-results js-not-filterable">Nothing to show</div>
            </div>
          </div>
        </div><!-- /.commitish-context-context -->
      </div>

    </li>
  </ul>

  <ul class="subnav with-scope">

    <li><a href="/bobbravo2/superfish-reloaded" class="selected" highlight="repo_source">Files</a></li>
    <li><a href="/bobbravo2/superfish-reloaded/commits/master" highlight="repo_commits">Commits</a></li>
    <li><a href="/bobbravo2/superfish-reloaded/branches" class="" highlight="repo_branches" rel="nofollow">Branches <span class="counter">1</span></a></li>
  </ul>

</div>

  
  
  


          

        </div><!-- /.repohead -->

        <div id="js-repo-pjax-container" data-pjax-container>
          




<!-- blob contrib key: blob_contributors:v21:8fafe7dc93c0a7cddb2ce44b0504a82c -->
<!-- blob contrib frag key: views10/v8/blob_contributors:v21:8fafe7dc93c0a7cddb2ce44b0504a82c -->

<!-- block_view_fragment_key: views10/v8/blob:v21:8212e4e17146d6224d7ec3f67689afb4 -->
  <div id="slider">

    <div class="breadcrumb" data-path="js/supersubs.js/">
      <b itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/bobbravo2/superfish-reloaded/tree/aefb802bf0fd6911336e488e63803115e9358490" class="js-rewrite-sha" itemprop="url"><span itemprop="title">superfish-reloaded</span></a></b> / <span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/bobbravo2/superfish-reloaded/tree/aefb802bf0fd6911336e488e63803115e9358490/js" class="js-rewrite-sha" itemscope="url"><span itemprop="title">js</span></a></span> / <strong class="final-path">supersubs.js</strong> <span class="js-clippy mini-icon mini-icon-clippy " data-clipboard-text="js/supersubs.js" data-copied-hint="copied!" data-copy-hint="copy to clipboard"></span>
    </div>

      
  <div class="commit file-history-tease js-blob-contributors-content" data-path="js/supersubs.js/">
    <img class="main-avatar" height="24" src="https://secure.gravatar.com/avatar/2e79ba6f473ec42412e6fa52c5e3c4ee?s=140&amp;d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-140.png" width="24" />
    <span class="author"><a href="/karevn">karevn</a></span>
    <time class="js-relative-date" datetime="2009-06-25T22:04:11-07:00" title="2009-06-25 22:04:11">June 25, 2009</time>
    <div class="commit-title">
        <a href="/bobbravo2/superfish-reloaded/commit/a6b3e91de37e21483da98f6d393fc48a627e67d9" class="message">Initial import</a>
    </div>

    <div class="participation">
      <p class="quickstat"><a href="#blob_contributors_box" rel="facebox"><strong>1</strong> contributor</a></p>
      
    </div>
    <div id="blob_contributors_box" style="display:none">
      <h2>Users on GitHub who have contributed to this file</h2>
      <ul class="facebox-user-list">
        <li>
          <img height="24" src="https://secure.gravatar.com/avatar/2e79ba6f473ec42412e6fa52c5e3c4ee?s=140&amp;d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-140.png" width="24" />
          <a href="/karevn">karevn</a>
        </li>
      </ul>
    </div>
  </div>


    <div class="frames">
      <div class="frame frame-center" data-path="js/supersubs.js/" data-permalink-url="/bobbravo2/superfish-reloaded/blob/aefb802bf0fd6911336e488e63803115e9358490/js/supersubs.js" data-title="superfish-reloaded/js/supersubs.js at master · bobbravo2/superfish-reloaded · GitHub" data-type="blob">

        <div id="files" class="bubble">
          <div class="file">
            <div class="meta">
              <div class="info">
                <span class="icon"><b class="mini-icon mini-icon-text-file"></b></span>
                <span class="mode" title="File Mode">file</span>
                  <span>91 lines (85 sloc)</span>
                <span>3.298 kb</span>
              </div>
              <ul class="button-group actions">
                  <li>
                    <a class="grouped-button file-edit-link minibutton bigger lighter js-rewrite-sha" href="/bobbravo2/superfish-reloaded/edit/aefb802bf0fd6911336e488e63803115e9358490/js/supersubs.js" data-method="post" rel="nofollow" data-hotkey="e">Edit</a>
                  </li>

                <li>
                  <a href="/bobbravo2/superfish-reloaded/raw/master/js/supersubs.js" class="minibutton btn-raw grouped-button bigger lighter" id="raw-url">Raw</a>
                </li>
                  <li>
                    <a href="/bobbravo2/superfish-reloaded/blame/master/js/supersubs.js" class="minibutton btn-blame grouped-button bigger lighter">Blame</a>
                  </li>
                <li>
                  <a href="/bobbravo2/superfish-reloaded/commits/master/js/supersubs.js" class="minibutton btn-history grouped-button bigger lighter" rel="nofollow">History</a>
                </li>
              </ul>
            </div>
              <div class="data type-javascript">
      <table cellpadding="0" cellspacing="0" class="lines">
        <tr>
          <td>
            <pre class="line_numbers"><span id="L1" rel="#L1">1</span>
<span id="L2" rel="#L2">2</span>
<span id="L3" rel="#L3">3</span>
<span id="L4" rel="#L4">4</span>
<span id="L5" rel="#L5">5</span>
<span id="L6" rel="#L6">6</span>
<span id="L7" rel="#L7">7</span>
<span id="L8" rel="#L8">8</span>
<span id="L9" rel="#L9">9</span>
<span id="L10" rel="#L10">10</span>
<span id="L11" rel="#L11">11</span>
<span id="L12" rel="#L12">12</span>
<span id="L13" rel="#L13">13</span>
<span id="L14" rel="#L14">14</span>
<span id="L15" rel="#L15">15</span>
<span id="L16" rel="#L16">16</span>
<span id="L17" rel="#L17">17</span>
<span id="L18" rel="#L18">18</span>
<span id="L19" rel="#L19">19</span>
<span id="L20" rel="#L20">20</span>
<span id="L21" rel="#L21">21</span>
<span id="L22" rel="#L22">22</span>
<span id="L23" rel="#L23">23</span>
<span id="L24" rel="#L24">24</span>
<span id="L25" rel="#L25">25</span>
<span id="L26" rel="#L26">26</span>
<span id="L27" rel="#L27">27</span>
<span id="L28" rel="#L28">28</span>
<span id="L29" rel="#L29">29</span>
<span id="L30" rel="#L30">30</span>
<span id="L31" rel="#L31">31</span>
<span id="L32" rel="#L32">32</span>
<span id="L33" rel="#L33">33</span>
<span id="L34" rel="#L34">34</span>
<span id="L35" rel="#L35">35</span>
<span id="L36" rel="#L36">36</span>
<span id="L37" rel="#L37">37</span>
<span id="L38" rel="#L38">38</span>
<span id="L39" rel="#L39">39</span>
<span id="L40" rel="#L40">40</span>
<span id="L41" rel="#L41">41</span>
<span id="L42" rel="#L42">42</span>
<span id="L43" rel="#L43">43</span>
<span id="L44" rel="#L44">44</span>
<span id="L45" rel="#L45">45</span>
<span id="L46" rel="#L46">46</span>
<span id="L47" rel="#L47">47</span>
<span id="L48" rel="#L48">48</span>
<span id="L49" rel="#L49">49</span>
<span id="L50" rel="#L50">50</span>
<span id="L51" rel="#L51">51</span>
<span id="L52" rel="#L52">52</span>
<span id="L53" rel="#L53">53</span>
<span id="L54" rel="#L54">54</span>
<span id="L55" rel="#L55">55</span>
<span id="L56" rel="#L56">56</span>
<span id="L57" rel="#L57">57</span>
<span id="L58" rel="#L58">58</span>
<span id="L59" rel="#L59">59</span>
<span id="L60" rel="#L60">60</span>
<span id="L61" rel="#L61">61</span>
<span id="L62" rel="#L62">62</span>
<span id="L63" rel="#L63">63</span>
<span id="L64" rel="#L64">64</span>
<span id="L65" rel="#L65">65</span>
<span id="L66" rel="#L66">66</span>
<span id="L67" rel="#L67">67</span>
<span id="L68" rel="#L68">68</span>
<span id="L69" rel="#L69">69</span>
<span id="L70" rel="#L70">70</span>
<span id="L71" rel="#L71">71</span>
<span id="L72" rel="#L72">72</span>
<span id="L73" rel="#L73">73</span>
<span id="L74" rel="#L74">74</span>
<span id="L75" rel="#L75">75</span>
<span id="L76" rel="#L76">76</span>
<span id="L77" rel="#L77">77</span>
<span id="L78" rel="#L78">78</span>
<span id="L79" rel="#L79">79</span>
<span id="L80" rel="#L80">80</span>
<span id="L81" rel="#L81">81</span>
<span id="L82" rel="#L82">82</span>
<span id="L83" rel="#L83">83</span>
<span id="L84" rel="#L84">84</span>
<span id="L85" rel="#L85">85</span>
<span id="L86" rel="#L86">86</span>
<span id="L87" rel="#L87">87</span>
<span id="L88" rel="#L88">88</span>
<span id="L89" rel="#L89">89</span>
<span id="L90" rel="#L90">90</span>
</pre>
          </td>
          <td width="100%">
                <div class="highlight"><pre><div class='line' id='LC1'><span class="cm">/*</span></div><div class='line' id='LC2'><span class="cm"> * Supersubs v0.2b - jQuery plugin</span></div><div class='line' id='LC3'><span class="cm"> * Copyright (c) 2008 Joel Birch</span></div><div class='line' id='LC4'><span class="cm"> *</span></div><div class='line' id='LC5'><span class="cm"> * Dual licensed under the MIT and GPL licenses:</span></div><div class='line' id='LC6'><span class="cm"> * 	http://www.opensource.org/licenses/mit-license.php</span></div><div class='line' id='LC7'><span class="cm"> * 	http://www.gnu.org/licenses/gpl.html</span></div><div class='line' id='LC8'><span class="cm"> *</span></div><div class='line' id='LC9'><span class="cm"> *</span></div><div class='line' id='LC10'><span class="cm"> * This plugin automatically adjusts submenu widths of suckerfish-style menus to that of</span></div><div class='line' id='LC11'><span class="cm"> * their longest list item children. If you use this, please expect bugs and report them</span></div><div class='line' id='LC12'><span class="cm"> * to the jQuery Google Group with the word &#39;Superfish&#39; in the subject line.</span></div><div class='line' id='LC13'><span class="cm"> *</span></div><div class='line' id='LC14'><span class="cm"> */</span></div><div class='line' id='LC15'><br/></div><div class='line' id='LC16'><span class="p">;(</span><span class="kd">function</span><span class="p">(</span><span class="nx">$</span><span class="p">){</span> <span class="c1">// $ will refer to jQuery within this closure</span></div><div class='line' id='LC17'><br/></div><div class='line' id='LC18'>	<span class="nx">$</span><span class="p">.</span><span class="nx">fn</span><span class="p">.</span><span class="nx">supersubs</span> <span class="o">=</span> <span class="kd">function</span><span class="p">(</span><span class="nx">options</span><span class="p">){</span></div><div class='line' id='LC19'>		<span class="kd">var</span> <span class="nx">opts</span> <span class="o">=</span> <span class="nx">$</span><span class="p">.</span><span class="nx">extend</span><span class="p">({},</span> <span class="nx">$</span><span class="p">.</span><span class="nx">fn</span><span class="p">.</span><span class="nx">supersubs</span><span class="p">.</span><span class="nx">defaults</span><span class="p">,</span> <span class="nx">options</span><span class="p">);</span></div><div class='line' id='LC20'>		<span class="c1">// return original object to support chaining</span></div><div class='line' id='LC21'>		<span class="k">return</span> <span class="k">this</span><span class="p">.</span><span class="nx">each</span><span class="p">(</span><span class="kd">function</span><span class="p">()</span> <span class="p">{</span></div><div class='line' id='LC22'>			<span class="c1">// cache selections</span></div><div class='line' id='LC23'>			<span class="kd">var</span> <span class="nx">$$</span> <span class="o">=</span> <span class="nx">$</span><span class="p">(</span><span class="k">this</span><span class="p">);</span></div><div class='line' id='LC24'>			<span class="c1">// support metadata</span></div><div class='line' id='LC25'>			<span class="kd">var</span> <span class="nx">o</span> <span class="o">=</span> <span class="nx">$</span><span class="p">.</span><span class="nx">meta</span> <span class="o">?</span> <span class="nx">$</span><span class="p">.</span><span class="nx">extend</span><span class="p">({},</span> <span class="nx">opts</span><span class="p">,</span> <span class="nx">$$</span><span class="p">.</span><span class="nx">data</span><span class="p">())</span> <span class="o">:</span> <span class="nx">opts</span><span class="p">;</span></div><div class='line' id='LC26'>			<span class="c1">// get the font size of menu.</span></div><div class='line' id='LC27'>			<span class="c1">// .css(&#39;fontSize&#39;) returns various results cross-browser, so measure an em dash instead</span></div><div class='line' id='LC28'>			<span class="kd">var</span> <span class="nx">fontsize</span> <span class="o">=</span> <span class="nx">$</span><span class="p">(</span><span class="s1">&#39;&lt;li id=&quot;menu-fontsize&quot;&gt;&amp;#8212;&lt;/li&gt;&#39;</span><span class="p">).</span><span class="nx">css</span><span class="p">({</span></div><div class='line' id='LC29'>				<span class="s1">&#39;padding&#39;</span> <span class="o">:</span> <span class="mi">0</span><span class="p">,</span></div><div class='line' id='LC30'>				<span class="s1">&#39;position&#39;</span> <span class="o">:</span> <span class="s1">&#39;absolute&#39;</span><span class="p">,</span></div><div class='line' id='LC31'>				<span class="s1">&#39;top&#39;</span> <span class="o">:</span> <span class="s1">&#39;-999em&#39;</span><span class="p">,</span></div><div class='line' id='LC32'>				<span class="s1">&#39;width&#39;</span> <span class="o">:</span> <span class="s1">&#39;auto&#39;</span></div><div class='line' id='LC33'>			<span class="p">}).</span><span class="nx">appendTo</span><span class="p">(</span><span class="nx">$$</span><span class="p">).</span><span class="nx">width</span><span class="p">();</span> <span class="c1">//clientWidth is faster, but was incorrect here</span></div><div class='line' id='LC34'>			<span class="c1">// remove em dash</span></div><div class='line' id='LC35'>			<span class="nx">$</span><span class="p">(</span><span class="s1">&#39;#menu-fontsize&#39;</span><span class="p">).</span><span class="nx">remove</span><span class="p">();</span></div><div class='line' id='LC36'>			<span class="c1">// cache all ul elements</span></div><div class='line' id='LC37'>			<span class="nx">$ULs</span> <span class="o">=</span> <span class="nx">$$</span><span class="p">.</span><span class="nx">find</span><span class="p">(</span><span class="s1">&#39;ul&#39;</span><span class="p">);</span></div><div class='line' id='LC38'>			<span class="c1">// loop through each ul in menu</span></div><div class='line' id='LC39'>			<span class="nx">$ULs</span><span class="p">.</span><span class="nx">each</span><span class="p">(</span><span class="kd">function</span><span class="p">(</span><span class="nx">i</span><span class="p">)</span> <span class="p">{</span>	</div><div class='line' id='LC40'>				<span class="c1">// cache this ul</span></div><div class='line' id='LC41'>				<span class="kd">var</span> <span class="nx">$ul</span> <span class="o">=</span> <span class="nx">$ULs</span><span class="p">.</span><span class="nx">eq</span><span class="p">(</span><span class="nx">i</span><span class="p">);</span></div><div class='line' id='LC42'>				<span class="c1">// get all (li) children of this ul</span></div><div class='line' id='LC43'>				<span class="kd">var</span> <span class="nx">$LIs</span> <span class="o">=</span> <span class="nx">$ul</span><span class="p">.</span><span class="nx">children</span><span class="p">();</span></div><div class='line' id='LC44'>				<span class="c1">// get all anchor grand-children</span></div><div class='line' id='LC45'>				<span class="kd">var</span> <span class="nx">$As</span> <span class="o">=</span> <span class="nx">$LIs</span><span class="p">.</span><span class="nx">children</span><span class="p">(</span><span class="s1">&#39;a&#39;</span><span class="p">);</span></div><div class='line' id='LC46'>				<span class="c1">// force content to one line and save current float property</span></div><div class='line' id='LC47'>				<span class="kd">var</span> <span class="nx">liFloat</span> <span class="o">=</span> <span class="nx">$LIs</span><span class="p">.</span><span class="nx">css</span><span class="p">(</span><span class="s1">&#39;white-space&#39;</span><span class="p">,</span><span class="s1">&#39;nowrap&#39;</span><span class="p">).</span><span class="nx">css</span><span class="p">(</span><span class="s1">&#39;float&#39;</span><span class="p">);</span></div><div class='line' id='LC48'>				<span class="c1">// remove width restrictions and floats so elements remain vertically stacked</span></div><div class='line' id='LC49'>				<span class="kd">var</span> <span class="nx">emWidth</span> <span class="o">=</span> <span class="nx">$ul</span><span class="p">.</span><span class="nx">add</span><span class="p">(</span><span class="nx">$LIs</span><span class="p">).</span><span class="nx">add</span><span class="p">(</span><span class="nx">$As</span><span class="p">).</span><span class="nx">css</span><span class="p">({</span></div><div class='line' id='LC50'>					<span class="s1">&#39;float&#39;</span> <span class="o">:</span> <span class="s1">&#39;none&#39;</span><span class="p">,</span></div><div class='line' id='LC51'>					<span class="s1">&#39;width&#39;</span>	<span class="o">:</span> <span class="s1">&#39;auto&#39;</span></div><div class='line' id='LC52'>				<span class="p">})</span></div><div class='line' id='LC53'>				<span class="c1">// this ul will now be shrink-wrapped to longest li due to position:absolute</span></div><div class='line' id='LC54'>				<span class="c1">// so save its width as ems. Clientwidth is 2 times faster than .width() - thanks Dan Switzer</span></div><div class='line' id='LC55'>				<span class="p">.</span><span class="nx">end</span><span class="p">().</span><span class="nx">end</span><span class="p">()[</span><span class="mi">0</span><span class="p">].</span><span class="nx">clientWidth</span> <span class="o">/</span> <span class="nx">fontsize</span><span class="p">;</span></div><div class='line' id='LC56'>				<span class="c1">// add more width to ensure lines don&#39;t turn over at certain sizes in various browsers</span></div><div class='line' id='LC57'>				<span class="nx">emWidth</span> <span class="o">+=</span> <span class="nx">o</span><span class="p">.</span><span class="nx">extraWidth</span><span class="p">;</span></div><div class='line' id='LC58'>				<span class="c1">// restrict to at least minWidth and at most maxWidth</span></div><div class='line' id='LC59'>				<span class="k">if</span> <span class="p">(</span><span class="nx">emWidth</span> <span class="o">&gt;</span> <span class="nx">o</span><span class="p">.</span><span class="nx">maxWidth</span><span class="p">)</span>		<span class="p">{</span> <span class="nx">emWidth</span> <span class="o">=</span> <span class="nx">o</span><span class="p">.</span><span class="nx">maxWidth</span><span class="p">;</span> <span class="p">}</span></div><div class='line' id='LC60'>				<span class="k">else</span> <span class="k">if</span> <span class="p">(</span><span class="nx">emWidth</span> <span class="o">&lt;</span> <span class="nx">o</span><span class="p">.</span><span class="nx">minWidth</span><span class="p">)</span>	<span class="p">{</span> <span class="nx">emWidth</span> <span class="o">=</span> <span class="nx">o</span><span class="p">.</span><span class="nx">minWidth</span><span class="p">;</span> <span class="p">}</span></div><div class='line' id='LC61'>				<span class="nx">emWidth</span> <span class="o">+=</span> <span class="s1">&#39;em&#39;</span><span class="p">;</span></div><div class='line' id='LC62'>				<span class="c1">// set ul to width in ems</span></div><div class='line' id='LC63'>				<span class="nx">$ul</span><span class="p">.</span><span class="nx">css</span><span class="p">(</span><span class="s1">&#39;width&#39;</span><span class="p">,</span><span class="nx">emWidth</span><span class="p">);</span></div><div class='line' id='LC64'>				<span class="c1">// restore li floats to avoid IE bugs</span></div><div class='line' id='LC65'>				<span class="c1">// set li width to full width of this ul</span></div><div class='line' id='LC66'>				<span class="c1">// revert white-space to normal</span></div><div class='line' id='LC67'>				<span class="nx">$LIs</span><span class="p">.</span><span class="nx">css</span><span class="p">({</span></div><div class='line' id='LC68'>					<span class="s1">&#39;float&#39;</span> <span class="o">:</span> <span class="nx">liFloat</span><span class="p">,</span></div><div class='line' id='LC69'>					<span class="s1">&#39;width&#39;</span> <span class="o">:</span> <span class="s1">&#39;100%&#39;</span><span class="p">,</span></div><div class='line' id='LC70'>					<span class="s1">&#39;white-space&#39;</span> <span class="o">:</span> <span class="s1">&#39;normal&#39;</span></div><div class='line' id='LC71'>				<span class="p">})</span></div><div class='line' id='LC72'>				<span class="c1">// update offset position of descendant ul to reflect new width of parent</span></div><div class='line' id='LC73'>				<span class="p">.</span><span class="nx">each</span><span class="p">(</span><span class="kd">function</span><span class="p">(){</span></div><div class='line' id='LC74'>					<span class="kd">var</span> <span class="nx">$childUl</span> <span class="o">=</span> <span class="nx">$</span><span class="p">(</span><span class="s1">&#39;&gt;ul&#39;</span><span class="p">,</span><span class="k">this</span><span class="p">);</span></div><div class='line' id='LC75'>					<span class="kd">var</span> <span class="nx">offsetDirection</span> <span class="o">=</span> <span class="nx">$childUl</span><span class="p">.</span><span class="nx">css</span><span class="p">(</span><span class="s1">&#39;left&#39;</span><span class="p">)</span><span class="o">!==</span><span class="kc">undefined</span> <span class="o">?</span> <span class="s1">&#39;left&#39;</span> <span class="o">:</span> <span class="s1">&#39;right&#39;</span><span class="p">;</span></div><div class='line' id='LC76'>					<span class="nx">$childUl</span><span class="p">.</span><span class="nx">css</span><span class="p">(</span><span class="nx">offsetDirection</span><span class="p">,</span><span class="nx">emWidth</span><span class="p">);</span></div><div class='line' id='LC77'>				<span class="p">});</span></div><div class='line' id='LC78'>			<span class="p">});</span></div><div class='line' id='LC79'><br/></div><div class='line' id='LC80'>		<span class="p">});</span></div><div class='line' id='LC81'>	<span class="p">};</span></div><div class='line' id='LC82'>	<span class="c1">// expose defaults</span></div><div class='line' id='LC83'>	<span class="nx">$</span><span class="p">.</span><span class="nx">fn</span><span class="p">.</span><span class="nx">supersubs</span><span class="p">.</span><span class="nx">defaults</span> <span class="o">=</span> <span class="p">{</span></div><div class='line' id='LC84'>		<span class="nx">minWidth</span>		<span class="o">:</span> <span class="mi">9</span><span class="p">,</span>		<span class="c1">// requires em unit.</span></div><div class='line' id='LC85'>		<span class="nx">maxWidth</span>		<span class="o">:</span> <span class="mi">25</span><span class="p">,</span>		<span class="c1">// requires em unit.</span></div><div class='line' id='LC86'>		<span class="nx">extraWidth</span>		<span class="o">:</span> <span class="mi">0</span>			<span class="c1">// extra width can ensure lines don&#39;t sometimes turn over due to slight browser differences in how they round-off values</span></div><div class='line' id='LC87'>	<span class="p">};</span></div><div class='line' id='LC88'><br/></div><div class='line' id='LC89'><span class="p">})(</span><span class="nx">jQuery</span><span class="p">);</span> <span class="c1">// plugin code ends</span></div><div class='line' id='LC90'><br/></div></pre></div>
          </td>
        </tr>
      </table>
  </div>

          </div>
        </div>
      </div>
    </div>

  </div>

<div class="frame frame-loading large-loading-area" style="display:none;" data-tree-list-url="/bobbravo2/superfish-reloaded/tree-list/aefb802bf0fd6911336e488e63803115e9358490" data-blob-url-prefix="/bobbravo2/superfish-reloaded/blob/aefb802bf0fd6911336e488e63803115e9358490">
  <img src="https://a248.e.akamai.net/assets.github.com/images/spinners/octocat-spinner-64.gif?1329872009" height="64" width="64">
</div>

        </div>
      </div>
      <div class="context-overlay"></div>
    </div>

      <div id="footer-push"></div><!-- hack for sticky footer -->
    </div><!-- end of wrapper - hack for sticky footer -->

      <!-- footer -->
      <div id="footer" >
        
  <div class="upper_footer">
     <div class="container clearfix">

       <!--[if IE]><h4 id="blacktocat_ie">GitHub Links</h4><![endif]-->
       <![if !IE]><h4 id="blacktocat">GitHub Links</h4><![endif]>

       <ul class="footer_nav">
         <h4>GitHub</h4>
         <li><a href="https://github.com/about">About</a></li>
         <li><a href="https://github.com/blog">Blog</a></li>
         <li><a href="https://github.com/features">Features</a></li>
         <li><a href="https://github.com/contact">Contact &amp; Support</a></li>
         <li><a href="https://github.com/training">Training</a></li>
         <li><a href="http://enterprise.github.com/">GitHub Enterprise</a></li>
         <li><a href="http://status.github.com/">Site Status</a></li>
       </ul>

       <ul class="footer_nav">
         <h4>Clients</h4>
         <li><a href="http://mac.github.com/">GitHub for Mac</a></li>
         <li><a href="http://windows.github.com/">GitHub for Windows</a></li>
         <li><a href="http://eclipse.github.com/">GitHub for Eclipse</a></li>
         <li><a href="http://mobile.github.com/">GitHub Mobile Apps</a></li>
       </ul>

       <ul class="footer_nav">
         <h4>Tools</h4>
         <li><a href="http://get.gaug.es/">Gauges: Web analytics</a></li>
         <li><a href="http://speakerdeck.com">Speaker Deck: Presentations</a></li>
         <li><a href="https://gist.github.com">Gist: Code snippets</a></li>

         <h4 class="second">Extras</h4>
         <li><a href="http://jobs.github.com/">Job Board</a></li>
         <li><a href="http://shop.github.com/">GitHub Shop</a></li>
         <li><a href="http://octodex.github.com/">The Octodex</a></li>
       </ul>

       <ul class="footer_nav">
         <h4>Documentation</h4>
         <li><a href="http://help.github.com/">GitHub Help</a></li>
         <li><a href="http://developer.github.com/">Developer API</a></li>
         <li><a href="http://github.github.com/github-flavored-markdown/">GitHub Flavored Markdown</a></li>
         <li><a href="http://pages.github.com/">GitHub Pages</a></li>
       </ul>

     </div><!-- /.site -->
  </div><!-- /.upper_footer -->

<div class="lower_footer">
  <div class="container clearfix">
    <!--[if IE]><div id="legal_ie"><![endif]-->
    <![if !IE]><div id="legal"><![endif]>
      <ul>
          <li><a href="https://github.com/site/terms">Terms of Service</a></li>
          <li><a href="https://github.com/site/privacy">Privacy</a></li>
          <li><a href="https://github.com/security">Security</a></li>
      </ul>

      <p>&copy; 2012 <span title="0.06588s from fe5.rs.github.com">GitHub</span> Inc. All rights reserved.</p>
    </div><!-- /#legal or /#legal_ie-->

      <div class="sponsor">
        <a href="http://www.rackspace.com" class="logo">
          <img alt="Dedicated Server" height="36" src="https://a248.e.akamai.net/assets.github.com/images/modules/footer/rackspaces_logo.png?1329521041" width="38" />
        </a>
        Powered by the <a href="http://www.rackspace.com ">Dedicated
        Servers</a> and<br/> <a href="http://www.rackspace.com/cloud">Cloud
        Computing</a> of Rackspace Hosting<span>&reg;</span>
      </div>
  </div><!-- /.site -->
</div><!-- /.lower_footer -->

      </div><!-- /#footer -->

    

<div id="keyboard_shortcuts_pane" class="instapaper_ignore readability-extra" style="display:none">
  <h2>Keyboard Shortcuts <small><a href="#" class="js-see-all-keyboard-shortcuts">(see all)</a></small></h2>

  <div class="columns threecols">
    <div class="column first">
      <h3>Site wide shortcuts</h3>
      <dl class="keyboard-mappings">
        <dt>s</dt>
        <dd>Focus site search</dd>
      </dl>
      <dl class="keyboard-mappings">
        <dt>?</dt>
        <dd>Bring up this help dialog</dd>
      </dl>
    </div><!-- /.column.first -->

    <div class="column middle" style='display:none'>
      <h3>Commit list</h3>
      <dl class="keyboard-mappings">
        <dt>j</dt>
        <dd>Move selection down</dd>
      </dl>
      <dl class="keyboard-mappings">
        <dt>k</dt>
        <dd>Move selection up</dd>
      </dl>
      <dl class="keyboard-mappings">
        <dt>c <em>or</em> o <em>or</em> enter</dt>
        <dd>Open commit</dd>
      </dl>
      <dl class="keyboard-mappings">
        <dt>y</dt>
        <dd>Expand URL to its canonical form</dd>
      </dl>
    </div><!-- /.column.first -->

    <div class="column last js-hidden-pane" style='display:none'>
      <h3>Pull request list</h3>
      <dl class="keyboard-mappings">
        <dt>j</dt>
        <dd>Move selection down</dd>
      </dl>
      <dl class="keyboard-mappings">
        <dt>k</dt>
        <dd>Move selection up</dd>
      </dl>
      <dl class="keyboard-mappings">
        <dt>o <em>or</em> enter</dt>
        <dd>Open issue</dd>
      </dl>
      <dl class="keyboard-mappings">
        <dt><span class="platform-mac">⌘</span><span class="platform-other">ctrl</span> <em>+</em> enter</dt>
        <dd>Submit comment</dd>
      </dl>
    </div><!-- /.columns.last -->

  </div><!-- /.columns.equacols -->

  <div class="js-hidden-pane" style='display:none'>
    <div class="rule"></div>

    <h3>Issues</h3>

    <div class="columns threecols">
      <div class="column first">
        <dl class="keyboard-mappings">
          <dt>j</dt>
          <dd>Move selection down</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>k</dt>
          <dd>Move selection up</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>x</dt>
          <dd>Toggle selection</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>o <em>or</em> enter</dt>
          <dd>Open issue</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt><span class="platform-mac">⌘</span><span class="platform-other">ctrl</span> <em>+</em> enter</dt>
          <dd>Submit comment</dd>
        </dl>
      </div><!-- /.column.first -->
      <div class="column last">
        <dl class="keyboard-mappings">
          <dt>c</dt>
          <dd>Create issue</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>l</dt>
          <dd>Create label</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>i</dt>
          <dd>Back to inbox</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>u</dt>
          <dd>Back to issues</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>/</dt>
          <dd>Focus issues search</dd>
        </dl>
      </div>
    </div>
  </div>

  <div class="js-hidden-pane" style='display:none'>
    <div class="rule"></div>

    <h3>Issues Dashboard</h3>

    <div class="columns threecols">
      <div class="column first">
        <dl class="keyboard-mappings">
          <dt>j</dt>
          <dd>Move selection down</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>k</dt>
          <dd>Move selection up</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>o <em>or</em> enter</dt>
          <dd>Open issue</dd>
        </dl>
      </div><!-- /.column.first -->
    </div>
  </div>

  <div class="js-hidden-pane" style='display:none'>
    <div class="rule"></div>

    <h3>Network Graph</h3>
    <div class="columns equacols">
      <div class="column first">
        <dl class="keyboard-mappings">
          <dt><span class="badmono">←</span> <em>or</em> h</dt>
          <dd>Scroll left</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt><span class="badmono">→</span> <em>or</em> l</dt>
          <dd>Scroll right</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt><span class="badmono">↑</span> <em>or</em> k</dt>
          <dd>Scroll up</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt><span class="badmono">↓</span> <em>or</em> j</dt>
          <dd>Scroll down</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>t</dt>
          <dd>Toggle visibility of head labels</dd>
        </dl>
      </div><!-- /.column.first -->
      <div class="column last">
        <dl class="keyboard-mappings">
          <dt>shift <span class="badmono">←</span> <em>or</em> shift h</dt>
          <dd>Scroll all the way left</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>shift <span class="badmono">→</span> <em>or</em> shift l</dt>
          <dd>Scroll all the way right</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>shift <span class="badmono">↑</span> <em>or</em> shift k</dt>
          <dd>Scroll all the way up</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>shift <span class="badmono">↓</span> <em>or</em> shift j</dt>
          <dd>Scroll all the way down</dd>
        </dl>
      </div><!-- /.column.last -->
    </div>
  </div>

  <div class="js-hidden-pane" >
    <div class="rule"></div>
    <div class="columns threecols">
      <div class="column first js-hidden-pane" >
        <h3>Source Code Browsing</h3>
        <dl class="keyboard-mappings">
          <dt>t</dt>
          <dd>Activates the file finder</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>l</dt>
          <dd>Jump to line</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>w</dt>
          <dd>Switch branch/tag</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>y</dt>
          <dd>Expand URL to its canonical form</dd>
        </dl>
      </div>
    </div>
  </div>

  <div class="js-hidden-pane" style='display:none'>
    <div class="rule"></div>
    <div class="columns threecols">
      <div class="column first">
        <h3>Browsing Commits</h3>
        <dl class="keyboard-mappings">
          <dt><span class="platform-mac">⌘</span><span class="platform-other">ctrl</span> <em>+</em> enter</dt>
          <dd>Submit comment</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>escape</dt>
          <dd>Close form</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>p</dt>
          <dd>Parent commit</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>o</dt>
          <dd>Other parent commit</dd>
        </dl>
      </div>
    </div>
  </div>

  <div class="js-hidden-pane" style='display:none'>
    <div class="rule"></div>
    <h3>Notifications</h3>

    <div class="columns threecols">
      <div class="column first">
        <dl class="keyboard-mappings">
          <dt>j</dt>
          <dd>Move selection down</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>k</dt>
          <dd>Move selection up</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>o <em>or</em> enter</dt>
          <dd>Open notification</dd>
        </dl>
      </div><!-- /.column.first -->

      <div class="column second">
        <dl class="keyboard-mappings">
          <dt>e <em>or</em> shift i <em>or</em> y</dt>
          <dd>Mark as read</dd>
        </dl>
        <dl class="keyboard-mappings">
          <dt>shift m</dt>
          <dd>Mute thread</dd>
        </dl>
      </div><!-- /.column.first -->
    </div>
  </div>

</div>

    <div id="markdown-help" class="instapaper_ignore readability-extra">
  <h2>Markdown Cheat Sheet</h2>

  <div class="cheatsheet-content">

  <div class="mod">
    <div class="col">
      <h3>Format Text</h3>
      <p>Headers</p>
      <pre>
# This is an &lt;h1&gt; tag
## This is an &lt;h2&gt; tag
###### This is an &lt;h6&gt; tag</pre>
     <p>Text styles</p>
     <pre>
*This text will be italic*
_This will also be italic_
**This text will be bold**
__This will also be bold__

*You **can** combine them*
</pre>
    </div>
    <div class="col">
      <h3>Lists</h3>
      <p>Unordered</p>
      <pre>
* Item 1
* Item 2
  * Item 2a
  * Item 2b</pre>
     <p>Ordered</p>
     <pre>
1. Item 1
2. Item 2
3. Item 3
   * Item 3a
   * Item 3b</pre>
    </div>
    <div class="col">
      <h3>Miscellaneous</h3>
      <p>Images</p>
      <pre>
![GitHub Logo](/images/logo.png)
Format: ![Alt Text](url)
</pre>
     <p>Links</p>
     <pre>
http://github.com - automatic!
[GitHub](http://github.com)</pre>
<p>Blockquotes</p>
     <pre>
As Kanye West said:

> We're living the future so
> the present is our past.
</pre>
    </div>
  </div>
  <div class="rule"></div>

  <h3>Code Examples in Markdown</h3>
  <div class="col">
      <p>Syntax highlighting with <a href="http://github.github.com/github-flavored-markdown/" title="GitHub Flavored Markdown" target="_blank">GFM</a></p>
      <pre>
```javascript
function fancyAlert(arg) {
  if(arg) {
    $.facebox({div:'#foo'})
  }
}
```</pre>
    </div>
    <div class="col">
      <p>Or, indent your code 4 spaces</p>
      <pre>
Here is a Python code example
without syntax highlighting:

    def foo:
      if not bar:
        return true</pre>
    </div>
    <div class="col">
      <p>Inline code for comments</p>
      <pre>
I think you should use an
`&lt;addr&gt;` element here instead.</pre>
    </div>
  </div>

  </div>
</div>


    <div id="ajax-error-message">
      <span class="mini-icon mini-icon-exclamation"></span>
      Something went wrong with that request. Please try again.
      <a href="#" class="ajax-error-dismiss">Dismiss</a>
    </div>

    <div id="logo-popup">
      <h2>Looking for the GitHub logo?</h2>
      <ul>
        <li>
          <h4>GitHub Logo</h4>
          <a href="http://github-media-downloads.s3.amazonaws.com/GitHub_Logos.zip"><img alt="Github_logo" src="https://a248.e.akamai.net/assets.github.com/images/modules/about_page/github_logo.png?1306884369" /></a>
          <a href="http://github-media-downloads.s3.amazonaws.com/GitHub_Logos.zip" class="minibutton btn-download download">Download</a>
        </li>
        <li>
          <h4>The Octocat</h4>
          <a href="http://github-media-downloads.s3.amazonaws.com/Octocats.zip"><img alt="Octocat" src="https://a248.e.akamai.net/assets.github.com/images/modules/about_page/octocat.png?1306884369" /></a>
          <a href="http://github-media-downloads.s3.amazonaws.com/Octocats.zip" class="minibutton btn-download download">Download</a>
        </li>
      </ul>
    </div>

    
    <span id='server_response_time' data-time='0.06805' data-host='fe5'></span>
  </body>
</html>

